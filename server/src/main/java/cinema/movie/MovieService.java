package cinema.movie;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.*;

@Service
public class MovieService {

    private MovieRepository movieRepository;
    private final Clock clock;

    public MovieService(MovieRepository movieRepository, Clock clock) {
        this.movieRepository = movieRepository;
        this.clock = clock;
    }

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this(movieRepository, Clock.systemDefaultZone());
    }

    /**
     * The movie is considered ongoing if at least one of conditions is met:
     * 1) There is seance of this movie today that is not started yet
     * 2) Some seances of this movie have started in the past, and some seances will start in the future
     */
    @Transactional
    public Set<Movie> ongoingMovies() {
        Instant now = clock.instant();
        Instant endOfToday = getEndOfToday();
        List<Movie> moviesWithSeancesToday = movieRepository.findAllWithSeancesBetween(now, endOfToday);
        List<Movie> moviesWithSeancesInPastAndFuture = movieRepository.findAllWithSeancesBeforeAndAfter(now);

        return Sets.union(
                new HashSet<>(moviesWithSeancesInPastAndFuture),
                new HashSet<>(moviesWithSeancesToday)
        );
    }

    /**
     * The movie is considered coming soon if at least one of conditions is met:
     * 1) There are no seances of this movie at all
     * 2) First seance of this movie starts later than today
     */
    @Transactional
    public Set<Movie> comingSoonMovies() {
        List<Movie> moviesWithSeancesAfterToday = movieRepository.findAllWithFirstSeanceAfter(getEndOfToday());
        List<Movie> moviesWithoutSeances = movieRepository.findAllWithoutSeances();

        return Sets.union(
                new HashSet<>(moviesWithSeancesAfterToday),
                new HashSet<>(moviesWithoutSeances)
        );
    }

    private Instant getEndOfToday() {
        return LocalDate.now(clock).plusDays(1)
                .atStartOfDay()
                .atZone(clock.getZone()).toInstant();
    }
}

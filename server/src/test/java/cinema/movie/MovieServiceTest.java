package cinema.movie;

import cinema.seance.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE) // todo embedded DB
public class MovieServiceTest {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    SeanceRepository seanceRepository;

    MovieService movieService;

    Instant now = LocalDateTime.of(2018, 7, 18, 14, 0).toInstant(ZoneOffset.UTC);
    Movie movie1, movie2, movie3;

    @Before
    public void init() {
        seanceRepository.deleteAll();
        movieRepository.deleteAll();
        Clock clock = Clock.fixed(now, ZoneId.of("UTC"));
        movieService = new MovieService(movieRepository, clock);

        movie1 = movieRepository.save(new Movie("movie1", 1, 1));
        movie2 = movieRepository.save(new Movie("movie2", 2, 2));
        movie3 = movieRepository.save(new Movie("movie3", 3, 3));
    }

    @Test
    public void ongoingEmpty() {
        addSeance(movie1, now.minus(3, ChronoUnit.DAYS)); // last seance is in the past - not ongoing
        addSeance(movie2, now.minus(3, ChronoUnit.DAYS)); // first seance later than today - not ongoing
        // movie3 has no seances at all, not ongoing

        checkOngoing();

        checkComingSoon(movie3);
    }


    @Test
    public void ongoingToday() {
        addSeance(movie1, now.plus(2, ChronoUnit.HOURS));

        checkOngoing(movie1);

        checkComingSoon(movie2, movie3);
    }

    @Test
    public void ongoingSeancesInPastAndFuture() {
        addSeance(movie1, now.minus(3, ChronoUnit.DAYS));
        addSeance(movie1, now.plus(3, ChronoUnit.DAYS));

        checkOngoing(movie1);

        checkComingSoon(movie2, movie3);
    }

    @Test
    public void comingSoonNoSeances() {
        checkComingSoon(movie1, movie2, movie3);

        checkOngoing();
    }

    @Test
    public void comingSoon() {
        addSeance(movie1, now.minus(2, ChronoUnit.DAYS));
        addSeance(movie2, now.minus(2, ChronoUnit.DAYS));
        addSeance(movie2, now.plus(2, ChronoUnit.DAYS));
        addSeance(movie3, now.plus(2, ChronoUnit.DAYS));

        checkComingSoon(movie3);

        checkOngoing(movie2);
    }

    private void addSeance(Movie movie, Instant startTime) {
        seanceRepository.save(new Seance(movie, null, startTime));
    }

    private void checkOngoing(Movie... expected) {
        Assert.assertEquals(new HashSet<>(Arrays.asList(expected)), movieService.ongoingMovies());
    }

    private void checkComingSoon(Movie... expected) {
        Assert.assertEquals(new HashSet<>(Arrays.asList(expected)), movieService.comingSoonMovies());
    }
}

package cinema.seance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.Instant;
import java.util.Collection;

@RepositoryRestResource
public interface SeanceRepository extends JpaRepository<Seance, Long> {
    Collection<Seance> findAllByMovieId(long movieId);
    Collection<Seance> findAllByStartTimeGreaterThan(Instant instant);
    Collection<Seance> findAllByMovieIdAndStartTimeGreaterThan(long movieId, Instant instant);
}

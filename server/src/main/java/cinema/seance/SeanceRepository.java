package cinema.seance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource
public interface SeanceRepository extends JpaRepository<Seance, Long> {
    Collection<Seance> findAllByMovieId(long movieId);
}

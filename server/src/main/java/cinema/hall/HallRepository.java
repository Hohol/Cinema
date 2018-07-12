package cinema.hall;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface HallRepository extends JpaRepository<Hall, Long> {
}

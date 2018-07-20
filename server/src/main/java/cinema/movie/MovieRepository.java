package cinema.movie;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.Instant;
import java.util.List;

@RepositoryRestResource
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query(value = "" +
            "select * from movie\n" +
            "where exists(\n" +
            " select 1 from seance\n" +
            " where movie.id = seance.movie_id and seance.start_time between ?1 and ?2\n" +
            ")",
            nativeQuery = true)
    List<Movie> findAllWithSeancesBetween(Instant start, Instant end);

    @Query(value = "" +
            "select * from movie\n" +
            "where id in (\n" +
            "  select movie_id from seance\n" +
            "  group by movie_id\n" +
            "  having ?1 between min(start_time) and max(start_time)\n" +
            ")",
            nativeQuery = true)
    List<Movie> findAllWithSeancesBeforeAndAfter(Instant instant);

    @Query(value = "" +
            "select * from movie\n" +
            "where id in (\n" +
            "  select movie_id from seance\n" +
            "  group by movie_id\n" +
            "  having min(start_time) > ?1\n" +
            ")",
            nativeQuery = true)
    List<Movie> findAllWithFirstSeanceAfter(Instant instant);

    @Query(value = "" +
            "select * from movie\n" +
            "where not exists (\n" +
            "  select 1 from seance\n" +
            "  where seance.movie_id = movie.id\n" +
            ")",
            nativeQuery = true)
    List<Movie> findAllWithoutSeances();
}
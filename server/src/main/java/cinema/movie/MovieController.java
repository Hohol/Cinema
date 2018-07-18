package cinema.movie;

import cinema.auth.CustomErrorType;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
class MovieController {
    @Autowired
    private MovieRepository repository;

    @GetMapping("/movies")
    public Collection<Movie> movies() {
        return repository.findAll();
    }

    @PostMapping("/movies/create")
    public Map<String, String> create(@RequestBody Movie movie) {
        repository.save(movie);
        return ImmutableMap.of("response", "Movie " + movie.getTitle() + " saved successfully");
    }

    @PostMapping("/movies/delete/{id}")
    public Object create(@PathVariable("id") long id) {
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(
                    new CustomErrorType("There are seances referencing this movie"),
                    HttpStatus.CONFLICT
            );
        }
        return ImmutableMap.of("response", "Movie deleted successfully");
    }
}

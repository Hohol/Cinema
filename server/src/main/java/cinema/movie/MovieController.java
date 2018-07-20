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
    @Autowired
    private MovieService movieService;

    @GetMapping("/movies")
    public Collection<Movie> movies(@RequestParam(value = "filter") Filter filter) {
        switch (filter) {
            case ongoing:
                return movieService.ongoingMovies();
            case comingSoon:
                return movieService.comingSoonMovies();
            case all:
                return repository.findAll();
        }
        throw new RuntimeException("unknown filter type");
    }

    enum Filter {
        ongoing, comingSoon, all
    }

    @GetMapping("/movies/{id}")
    public Movie movie(@PathVariable("id") long id) {
        return repository.getOne(id);
    }

    @PostMapping("/movies/create")
    public Map<String, String> create(@RequestBody Movie movie) {
        repository.save(movie);
        return ImmutableMap.of("response", "Movie " + movie.getTitle() + " saved successfully");
    }

    @PostMapping("/movies/edit")
    public Map<String, String> edit(@RequestBody Movie movie) {
        repository.save(movie);
        return ImmutableMap.of("response", "Movie " + movie.getTitle() + " edited successfully");
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

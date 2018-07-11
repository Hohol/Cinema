package cinema.movie;

import org.springframework.beans.factory.annotation.Autowired;
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
    public String create(@RequestBody Movie movie) {
        repository.save(movie);
        return "Movie " + movie.title + "saved successfully";
    }
}

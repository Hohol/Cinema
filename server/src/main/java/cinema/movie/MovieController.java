package cinema.movie;

import com.google.common.collect.ImmutableMap;
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
    public Map<String, String> create(@RequestBody Movie movie) {
        repository.save(movie);
        System.out.println(movie);
        return ImmutableMap.of("response", "Movie " + movie.title + " saved successfully");
    }
}

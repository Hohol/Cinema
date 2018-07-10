package cinema;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class MovieController {
    private MovieRepository repository;

    public MovieController(MovieRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/movies")
    public Collection<Movie> movies() {
        return repository.findAll();
    }
}

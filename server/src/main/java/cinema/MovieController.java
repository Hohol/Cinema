package cinema;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
public class MovieController {
    private MovieRepository repository;

    public MovieController(MovieRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/movies")
    public Collection<Movie> movies() {
        return repository.findAll().stream().filter(m -> m.getName().contains("u")).collect(Collectors.toList());
    }
}

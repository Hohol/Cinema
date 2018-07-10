package cinema.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
class MovieController {
    @Autowired
    private MovieRepository repository;

    @GetMapping("/movies")
    public Collection<Movie> movies() {
        return repository.findAll();
    }
}

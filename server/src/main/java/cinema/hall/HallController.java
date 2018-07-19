package cinema.hall;

import cinema.auth.CustomErrorType;
import cinema.movie.Movie;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
class HallController {
    @Autowired
    private HallRepository hallRepository;

    @GetMapping("/halls")
    public Collection<Hall> halls() {
        return hallRepository.findAll();
    }

    @GetMapping("/halls/{id}")
    public Hall movie(@PathVariable("id") long id) {
        return hallRepository.getOne(id);
    }

    @PostMapping("/halls/delete/{id}")
    public Object create(@PathVariable("id") long id) {
        try {
            hallRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(
                    new CustomErrorType("There are seances referencing this hall"),
                    HttpStatus.CONFLICT
            );
        }
        return ImmutableMap.of("response", "Hall deleted successfully");
    }

    @PostMapping("/halls/create")
    public Map<String, String> create(@RequestBody Hall hall) {
        hallRepository.save(hall);
        return ImmutableMap.of("response", "Hall " + hall.getName() + " saved successfully");
    }

    @PostMapping("/halls/edit")
    public Map<String, String> edit(@RequestBody Hall hall) {
        hallRepository.save(hall);
        return ImmutableMap.of("response", "Hall " + hall.getName() + " edited successfully");
    }
}


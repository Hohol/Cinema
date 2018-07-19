package cinema.hall;

import cinema.auth.CustomErrorType;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
class HallController {
    @Autowired
    private HallRepository hallRepository;

    @GetMapping("/halls")
    public Collection<Hall> halls() {
        return hallRepository.findAll();
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
}


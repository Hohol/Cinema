package cinema.hall;

import org.springframework.beans.factory.annotation.Autowired;
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
}


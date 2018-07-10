package cinema.seance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
class SeanceController {
    @Autowired
    private SeanceRepository repository;

    @GetMapping("/seances")
    public Collection<Seance> seances() {
        return repository.findAll();
    }
}

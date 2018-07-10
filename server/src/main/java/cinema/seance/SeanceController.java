package cinema.seance;

import cinema.auth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
class SeanceController {
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/seances")
    public Collection<Seance> seances(Principal principal) {
        List<Seance> seances = seanceRepository.findAll();
        if (principal != null) {
            User user = userRepository.findOneByUsername(principal.getName());
            for (Seance seance : seances) {
                seance.price = calculatePrice(seance, user);
            }
        }
        return seances;
    }

    private int calculatePrice(Seance seance, User user) {
        double factor = 1;
        LocalDate today = LocalDate.now();
        if (today.getDayOfYear() == user.getBirthday().getDayOfYear()) {
            factor -= 0.5;
        }
        if (ChronoUnit.YEARS.between(user.getBirthday(), today) < 14) {
            factor -= 0.25;
        }
        return (int) (seance.movie.baseTicketPrice * factor);
    }
}

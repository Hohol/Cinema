package cinema.seance;

import cinema.auth.*;
import cinema.hall.Position;
import cinema.ticket.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@RestController
class SeanceController {
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/seances")
    public Collection<SeanceForApi> seances(Principal principal) {
        return seanceRepository.findAll().stream()
                .map(s -> seanceForApi(s, principal))
                .collect(Collectors.toList());
    }

    @GetMapping("/seance/{id}")
    public SeanceForApi seance(Principal principal, @PathVariable("id") long id) {
        Seance seance = (Seance) Hibernate.unproxy(seanceRepository.getOne(id)); // todo eager loading instead?
        return seanceForApi(seance, principal);
    }

    private SeanceForApi seanceForApi(Seance seance, Principal principal) {
        return new SeanceForApi(seance, calculatePrice(seance, principal), getOccupiedPositions(seance));
    }

    private List<Position> getOccupiedPositions(Seance seance) {
        return ticketRepository.findAllBySeanceId(seance.id)
                .stream()
                .map(t -> t.position)
                .collect(Collectors.toList());
    }

    private int calculatePrice(Seance seance, Principal principal) {
        return (int) Math.round(seance.movie.baseTicketPrice * getDiscountFactor(principal));
    }

    private double getDiscountFactor(Principal principal) {
        if (principal == null) {
            return 1;
        }
        User user = userRepository.findOneByUsername(principal.getName());
        double factor = 1;
        LocalDate today = LocalDate.now();
        if (today.getDayOfYear() == user.getBirthday().getDayOfYear()) {
            factor -= 0.5;
        }
        if (ChronoUnit.YEARS.between(user.getBirthday(), today) < 14) {
            factor -= 0.25;
        }
        return factor;
    }
}

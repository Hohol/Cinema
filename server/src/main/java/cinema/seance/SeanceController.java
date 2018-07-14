package cinema.seance;

import cinema.auth.*;
import cinema.hall.Position;
import cinema.ticket.*;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
    public Collection<SeanceForApi> seances(
            Principal principal,
            @RequestParam(value = "movieId", required = false) Long movieId
    ) {
        Collection<Seance> seances;
        if (movieId != null) {
            seances = seanceRepository.findAllByMovieId(movieId);
        } else {
            seances = seanceRepository.findAll();
        }
        return seances.stream()
                .map(s -> seanceForApi(s, principal))
                .collect(Collectors.toList());
    }

    @GetMapping("/seance/{id}")
    public SeanceForApi seance(Principal principal, @PathVariable("id") long id) {
        Seance seance = seanceRepository.getOne(id);
        return seanceForApi(seance, principal);
    }

    @PostMapping("/seance/calculate-price/{id}")
    public int getPrice(Principal principal, @PathVariable("id") long seanceId, @RequestBody List<Position> selected) {
        int sumPrice = 0;
        Seance seance = seanceRepository.getOne(seanceId);
        Set<Position> vip = new HashSet<>(seance.getHall().vipPositions);
        Set<Position> occupied = new HashSet<>(getOccupiedPositions(seance));
        for (Position pos : selected) {
            if (occupied.contains(pos)) {
                throw new RuntimeException("Trying to buy occupied place");
            }
            double price = calculateFixedPrice(seance, principal);
            if (vip.contains(pos)) {
                price *= seance.getHall().getVipFactor();
            }
            sumPrice += Math.round(price);
        }
        return sumPrice;
    }

    @PostMapping("/seance/buy/{id}")
    @Transactional
    public Map<String, String> buy(Principal principal, @PathVariable("id") long seanceId, @RequestBody List<Position> selected) {
        User user = principal == null ? null : userRepository.findOneByUsername(principal.getName());
        Seance seance = seanceRepository.getOne(seanceId);
        List<Ticket> tickets = selected.stream()
                .map(pos -> new Ticket(seance, user, pos))
                .collect(Collectors.toList());
        ticketRepository.saveAll(tickets);
        return ImmutableMap.of("response", "tickets bought successfully");
    }

    private SeanceForApi seanceForApi(Seance seance, Principal principal) {
        return new SeanceForApi(seance, calculateFixedPrice(seance, principal), getOccupiedPositions(seance));
    }

    private List<Position> getOccupiedPositions(Seance seance) {
        return ticketRepository.findAllBySeanceId(seance.getId())
                .stream()
                .map(Ticket::getPosition)
                .collect(Collectors.toList());
    }

    private int calculateFixedPrice(Seance seance, Principal principal) {
        return (int) Math.round(seance.getMovie().getBaseTicketPrice() * getDiscountFactor(principal));
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

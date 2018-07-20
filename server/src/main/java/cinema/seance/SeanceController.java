package cinema.seance;

import cinema.auth.*;
import cinema.hall.*;
import cinema.movie.*;
import cinema.ticket.*;
import com.google.common.base.Verify;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.*;
import java.util.*;
import java.util.stream.*;

@RestController
class SeanceController {
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private PriceCalculator priceCalculator;

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private HallRepository hallRepository;

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
                .map(s -> seanceForApi(s, getUser(principal)))
                .collect(Collectors.toList());
    }

    @GetMapping("/seance/{id}")
    public SeanceForApi seance(Principal principal, @PathVariable("id") long id) {
        Seance seance = seanceRepository.getOne(id);
        return seanceForApi(seance, getUser(principal));
    }

    private static class NewSeance {
        public Long id;
        public long movieId;
        public String startTime;
        public long hallId;

        @Override
        public String toString() {
            return "NewSeance{" +
                    "id=" + id +
                    ", movieId=" + movieId +
                    ", startTime='" + startTime + '\'' +
                    ", hallId=" + hallId +
                    '}';
        }
    }

    @PostMapping("/seances/create")
    @Transactional
    public void create(@RequestBody NewSeance newSeance) {
        Movie movie = movieRepository.getOne(newSeance.movieId);
        Hall hall = hallRepository.getOne(newSeance.hallId);
        Instant startTime = Instant.parse(newSeance.startTime);
        Seance seance = new Seance(movie, hall, startTime);
        seanceRepository.save(seance);
    }

    @PostMapping("/seances/edit")
    @Transactional
    public void edit(@RequestBody NewSeance newSeance) {
        Movie movie = movieRepository.getOne(newSeance.movieId);
        Hall hall = hallRepository.getOne(newSeance.hallId);
        Instant startTime = Instant.parse(newSeance.startTime);

        Seance seance = seanceRepository.getOne(newSeance.id);
        seance.setMovie(movie);
        seance.setHall(hall);
        seance.setStartTime(startTime);

        seanceRepository.save(seance);
    }

    @PostMapping("/seances/delete/{id}")
    public Object delete(@PathVariable("id") long id) {
        seanceRepository.deleteById(id);
        return ImmutableMap.of("response", "Seance deleted successfully");
    }

    @PostMapping("/seance/calculate-price/{id}")
    @Transactional
    public int getPrice(Principal principal, @PathVariable("id") long seanceId, @RequestBody List<Position> selected) {
        Seance seance = seanceRepository.getOne(seanceId);
        Set<Position> occupied = new HashSet<>(getOccupiedPositions(seance));
        Verify.verify(Collections.disjoint(selected, occupied), "Trying to buy occupied position");
        User user = getUser(principal);
        int boughtTicketsCnt = getBoughtTicketsCnt(user);
        List<Integer> prices = priceCalculator.calculateSelectedPositionsPrice(seance, user, boughtTicketsCnt, selected);
        return prices.stream().mapToInt(p -> p).sum();
    }

    @PostMapping("/seance/buy/{id}")
    @Transactional
    public Map<String, String> buy(Principal principal, @PathVariable("id") long seanceId, @RequestBody List<Position> selected) {
        User user = getUser(principal);
        Seance seance = seanceRepository.getOne(seanceId);
        Set<Position> occupied = new HashSet<>(getOccupiedPositions(seance));
        Verify.verify(Collections.disjoint(selected, occupied), "Trying to buy occupied position");

        List<Integer> prices = priceCalculator.calculateFinalPrice(seance, user, getBoughtTicketsCnt(user), selected);
        List<Ticket> tickets = IntStream.range(0, selected.size())
                .mapToObj(i -> new Ticket(seance, user, selected.get(i), prices.get(i)))
                .collect(Collectors.toList());

        ticketRepository.saveAll(tickets);
        return ImmutableMap.of("response", "tickets bought successfully");
    }

    @GetMapping("/seance-stats/{id}")
    @Transactional
    public SeanceStats seanceStats(Principal principal, @PathVariable("id") long seanceId) {
        Seance seance = seanceRepository.getOne(seanceId);
        List<Ticket> tickets = ticketRepository.findAllBySeanceId(seanceId);
        return new SeanceStats(seance, tickets);
    }

    private SeanceForApi seanceForApi(Seance seance, User user) {
        int price = priceCalculator.calculateFixedPrice(seance, user);
        return new SeanceForApi(seance, price, getOccupiedPositions(seance));
    }

    private List<Position> getOccupiedPositions(Seance seance) {
        return ticketRepository.findAllBySeanceId(seance.getId())
                .stream()
                .map(Ticket::getPosition)
                .collect(Collectors.toList());
    }

    private User getUser(Principal principal) {
        return principal == null ? null : userRepository.findOneByUsername(principal.getName());
    }

    private int getBoughtTicketsCnt(User user) {
        if (user == null) {
            return 0;
        }
        return ticketRepository.countByUserId(user.getId());
    }
}

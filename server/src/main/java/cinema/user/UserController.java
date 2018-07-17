package cinema.user;

import cinema.auth.*;
import cinema.ticket.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @CrossOrigin
    @GetMapping("/users")
    public Collection<User> users() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    @Transactional
    public UserStats seance(Principal principal, @PathVariable("id") long id) {
        User user = userRepository.getOne(id);
        List<Ticket> tickets = ticketRepository.findAllByUserId(id);
        return new UserStats(user, tickets);
    }
}

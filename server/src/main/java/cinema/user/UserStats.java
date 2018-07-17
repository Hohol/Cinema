package cinema.user;

import cinema.auth.User;
import cinema.ticket.Ticket;

import java.util.List;

public class UserStats {
    public final User user;
    public final List<Ticket> tickets;

    public UserStats(User user, List<Ticket> tickets) {
        this.user = user;
        this.tickets = tickets;
    }
}

package cinema.seance;

import cinema.ticket.Ticket;

import java.util.List;

public class SeanceStats {
    public final Seance seance;
    public final List<Ticket> tickets;

    public SeanceStats(Seance seance, List<Ticket> tickets) {
        this.seance = seance;
        this.tickets = tickets;
    }
}

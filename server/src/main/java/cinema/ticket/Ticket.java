package cinema.ticket;

import cinema.auth.User;
import cinema.hall.Position;
import cinema.seance.Seance;

import javax.persistence.*;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Seance seance;

    @ManyToOne
    private User user;

    @Embedded
    private Position position;

    public Ticket() {
    }

    public Ticket(Seance seance, User user, Position position) {
        this.seance = seance;
        this.user = user;
        this.position = position;
    }

    public long getId() {
        return id;
    }

    public Seance getSeance() {
        return seance;
    }

    public User getUser() {
        return user;
    }

    public Position getPosition() {
        return position;
    }
}

package cinema.ticket;

import cinema.auth.User;
import cinema.hall.Position;
import cinema.seance.Seance;

import javax.persistence.*;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @ManyToOne
    public Seance seance;

    @ManyToOne
    public User user;

    @Embedded
    public Position position;

    public Ticket() {
    }

    public Ticket(Seance seance, User user, Position position) {
        this.seance = seance;
        this.user = user;
        this.position = position;
    }
}

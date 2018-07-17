package cinema.ticket;

import cinema.auth.User;
import cinema.hall.Position;
import cinema.seance.Seance;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.annotation.Nullable;
import javax.persistence.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Seance seance;

    @ManyToOne
    @Nullable
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

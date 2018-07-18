package cinema.ticket;

import cinema.auth.User;
import cinema.hall.Position;
import cinema.seance.Seance;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.*;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Seance seance;

    @ManyToOne
    @Nullable
    private User user;

    @Embedded
    private Position position;

    private int price;

    public Ticket() {
    }

    public Ticket(Seance seance, User user, Position position, int price) {
        this.seance = seance;
        this.user = user;
        this.position = position;
        this.price = price;
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

    public int getPrice() {
        return price;
    }
}

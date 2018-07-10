package cinema.movie;

import javax.persistence.*;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String title;
    public int durationMinutes;
    public int baseTicketPrice;

    public Movie() {
    }

    public Movie(String title, int durationMinutes, int baseTicketPrice) {
        this.title = title;
        this.durationMinutes = durationMinutes;
        this.baseTicketPrice = baseTicketPrice;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", durationMinutes=" + durationMinutes +
                ", baseTicketPrice=" + baseTicketPrice +
                '}';
    }
}
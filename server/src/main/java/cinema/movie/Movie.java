package cinema.movie;

import javax.persistence.*;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private int durationMinutes;
    private int baseTicketPrice;

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

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public int getBaseTicketPrice() {
        return baseTicketPrice;
    }
}
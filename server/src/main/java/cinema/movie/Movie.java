package cinema.movie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id &&
                durationMinutes == movie.durationMinutes &&
                baseTicketPrice == movie.baseTicketPrice &&
                Objects.equals(title, movie.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, durationMinutes, baseTicketPrice);
    }
}
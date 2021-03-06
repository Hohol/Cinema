package cinema.seance;

import cinema.hall.Hall;
import cinema.movie.Movie;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.Instant;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Seance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Hall hall;

    private Instant startTime;

    public Seance() {
    }

    public Seance(Movie movie, Hall hall, Instant startTime) {
        this.movie = movie;
        this.hall = hall;
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "Seance{" +
                "id=" + id +
                ", movie=" + movie +
                ", hall=" + hall +
                ", startTime=" + startTime +
                '}';
    }

    public Hall getHall() {
        return hall;
    }

    public Movie getMovie() {
        return movie;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public long getId() {
        return id;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }
}

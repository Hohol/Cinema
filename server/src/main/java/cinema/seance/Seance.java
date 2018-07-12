package cinema.seance;

import cinema.hall.Hall;
import cinema.movie.Movie;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Seance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @ManyToOne
    public Movie movie;

    @ManyToOne
    public Hall hall;

    public Instant startTime;

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
}

package cinema.seance;

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

    public Instant startTime;

    @Transient
    public int price;

    public Seance() {
    }

    public Seance(Movie movie, Instant startTime) {
        this.movie = movie;
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "Seance{" +
                "id=" + id +
                ", movie=" + movie +
                ", startTime=" + startTime +
                ", price=" + price +
                '}';
    }
}

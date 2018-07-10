package cinema.seance;

import cinema.movie.Movie;

import javax.persistence.*;

@Entity
public class Seance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @ManyToOne
    public Movie movie;

    public Seance() {
    }

    public Seance(Movie movie) {
        this.movie = movie;
    }
}

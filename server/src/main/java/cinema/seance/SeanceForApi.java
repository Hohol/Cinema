package cinema.seance;

import cinema.hall.Hall;
import cinema.movie.Movie;

import java.time.Instant;

public class SeanceForApi {
    // Seance fields
    public final long id;
    public final Movie movie;
    public final Hall hall;
    public final Instant startTime;

    // calculated fields
    public final int price;

    public SeanceForApi(Seance seance, int price) {
        this.id = seance.id;
        this.movie = seance.movie;
        this.hall = seance.hall;
        this.startTime = seance.startTime;
        this.price = price;
    }
}

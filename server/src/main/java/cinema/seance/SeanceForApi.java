package cinema.seance;

import cinema.hall.*;
import cinema.movie.Movie;

import java.time.Instant;
import java.util.List;

public class SeanceForApi {
    // Seance fields
    public final long id;
    public final Movie movie;
    public final Hall hall;
    public final Instant startTime;

    // calculated fields
    public final int price;
    public final List<Position> occupiedPositions;

    public SeanceForApi(Seance seance, int price, List<Position> occupiedPositions) {
        this.id = seance.id;
        this.movie = seance.movie;
        this.hall = seance.hall;
        this.startTime = seance.startTime;
        this.price = price;
        this.occupiedPositions = occupiedPositions;
    }
}

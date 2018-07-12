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
        this.id = seance.getId();
        this.movie = seance.getMovie();
        this.hall = seance.getHall();
        this.startTime = seance.getStartTime();
        this.price = price;
        this.occupiedPositions = occupiedPositions;
    }
}

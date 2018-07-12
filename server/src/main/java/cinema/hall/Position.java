package cinema.hall;

import java.io.Serializable;

public class Position implements Serializable {
    public int row;
    public int col;

    public Position() {
    }

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }
}

package cinema.hall;

import javax.persistence.*;
import java.util.Arrays;

@Entity
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public String name;

    public double vipFactor;

    public int rowCnt;
    public int colCnt;

    public Position[] vipPositions;

    public Hall() {
    }

    public Hall(String name, double vipFactor, int rowCnt, int colCnt, Position[] vipPositions) {
        this.name = name;
        this.vipFactor = vipFactor;
        this.rowCnt = rowCnt;
        this.colCnt = colCnt;
        this.vipPositions = vipPositions;
    }

    @Override
    public String toString() {
        return "Hall{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rowCnt=" + rowCnt +
                ", colCnt=" + colCnt +
                ", vipPositions=" + Arrays.toString(vipPositions) +
                '}';
    }
}

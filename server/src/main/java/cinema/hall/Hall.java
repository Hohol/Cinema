package cinema.hall;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private double vipFactor;

    private int rowCnt;
    private int colCnt;

    @ElementCollection
    public List<Position> vipPositions;

    public Hall() {
    }

    public Hall(String name, double vipFactor, int rowCnt, int colCnt, List<Position> vipPositions) {
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
                ", vipPositions=" + vipPositions +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getVipFactor() {
        return vipFactor;
    }

    public int getRowCnt() {
        return rowCnt;
    }

    public int getColCnt() {
        return colCnt;
    }

    public List<Position> getVipPositions() {
        return vipPositions;
    }
}

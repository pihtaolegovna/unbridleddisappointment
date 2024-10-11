package sakura.arachnida.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "flight")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('flight_flightid_seq')")
    @Column(name = "flightid", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board")
    private Board board;

    @NotNull(message = "Departure time cannot be null")
    @Column(name = "departuretime", nullable = false)
    private String departuretime;

    @NotNull(message = "Arrival time cannot be null")
    @Column(name = "arrivaltime", nullable = false)
    private String arrivaltime;

    @NotBlank(message = "Departure place cannot be blank")
    @Column(name = "departureplace", nullable = false)
    private String departureplace;

    @NotBlank(message = "Arrival place cannot be blank")
    @Column(name = "arrivalplace", nullable = false)
    private String arrivalplace;

    @Column(name = "is_deleted", nullable = false)
    @ColumnDefault("false")
    private boolean isDeleted;

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getId() {
        return id;
    }

    public void setNumber(Integer id) {
        this.board = board;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getDeparturetime() {
        return departuretime;
    }

    public void setDeparturetime(String departuretime) {
        this.departuretime = departuretime;
    }

    public String getArrivaltime() {
        return arrivaltime;
    }

    public void setArrivaltime(String arrivaltime) {
        this.arrivaltime = arrivaltime;
    }

    public String getDepartureplace() {
        return departureplace;
    }

    public void setDepartureplace(String departureplace) {
        this.departureplace = departureplace;
    }

    public String getArrivalplace() {
        return arrivalplace;
    }

    public void setArrivalplace(String arrivalplace) {
        this.arrivalplace = arrivalplace;
    }

    public Integer getNumber() { return board.getId();
    }
}
package sakura.arachnida.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "flightseat")
public class Flightseat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('flightseat_flightseatid_seq')")
    @Column(name = "flightseatid", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat")
    private Boardseat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight")
    private Flight flight;

    @Size(max = 20, message = "Status must not exceed 20 characters")
    @Column(name = "status")
    private String status;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be a positive number")
    @Column(name = "price", nullable = false)
    private Double price;

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

    public void setId(Integer id) {
        this.id = id;
    }

    public Boardseat getSeat() {
        return seat;
    }

    public void setSeat(Boardseat seat) {
        this.seat = seat;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
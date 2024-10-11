package sakura.arachnida.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.ColumnDefault;


@Entity
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('board_boardid_seq')")
    @Column(name = "boardid", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model", nullable = false)
    @NotNull(message = "Model must not be null")
    private Model model;

    @Column(name = "boardnumber", nullable = false)
    @NotBlank(message = "Board number cannot be empty")
    @Size(min = 2, max = 50, message = "Board number must be between 2 and 50 characters")
    private String boardnumber;

    @Column(name = "year", nullable = false)
    @NotNull(message = "Year must not be null")
    @Min(value = 1900, message = "Year cannot be less than 1900")
    @Max(value = 2100, message = "Year cannot be more than 2100")
    private Integer year;

    @Column(name = "seatsamount")
    @Positive(message = "Seats amount must be a positive number")
    private Integer seatsamount;

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

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public String getBoardnumber() {
        return boardnumber;
    }

    public void setBoardnumber(String boardnumber) {
        this.boardnumber = boardnumber;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getSeatsamount() {
        return seatsamount;
    }

    public void setSeatsamount(Integer seatsamount) {
        this.seatsamount = seatsamount;
    }
}
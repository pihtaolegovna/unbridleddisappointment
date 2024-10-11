package sakura.arachnida.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "boardseat")
public class Boardseat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('boardseat_boardseatid_seq')")
    @Column(name = "boardseatid", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board")
    private Board board;

    @Size(max = 50, message = "Seat type must be less than or equal to 50 characters")
    @NotBlank(message = "Seat type cannot be blank")
    @Column(name = "seattype", length = 50)
    private String seattype;

    @NotNull(message = "Row number cannot be null")
    @Min(value = 1, message = "Row number must be at least 1")
    @Column(name = "rownumber", nullable = false)
    private Integer rownumber;

    @NotNull(message = "Seat number cannot be null")
    @Min(value = 1, message = "Seat number must be at least 1")
    @Column(name = "seatnumber", nullable = false)
    private Integer seatnumber;

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

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getSeattype() {
        return seattype;
    }

    public void setSeattype(String seattype) {
        this.seattype = seattype;
    }

    public Integer getRownumber() {
        return rownumber;
    }

    public void setRownumber(Integer rownumber) {
        this.rownumber = rownumber;
    }

    public Integer getSeatnumber() {
        return seatnumber;
    }

    public void setSeatnumber(Integer seatnumber) {
        this.seatnumber = seatnumber;
    }

}
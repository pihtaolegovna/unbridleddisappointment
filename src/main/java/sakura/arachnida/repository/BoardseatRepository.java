package sakura.arachnida.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sakura.arachnida.models.Boardseat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public interface BoardseatRepository extends JpaRepository<Boardseat, Integer> {

    default Page<Boardseat> findAllWithPaginationAndFilters(
            Pageable pageable,
            String filterSeatType,
            String filterRowNumber,
            String filterBoard,
            Boolean isDeleted,
            String sortField,
            String sortDirection) {

        List<Boardseat> allBoardSeats = findAll();

        List<Boardseat> filteredBoardSeats = allBoardSeats.stream()
                .filter(boardseat ->
                        (filterSeatType == null || boardseat.getSeattype().contains(filterSeatType)) &&
                                (filterRowNumber == null || boardseat.getRownumber().toString().contains(filterRowNumber)) &&
                                (filterBoard == null || boardseat.getBoard().getBoardnumber().contains(filterBoard)) &&  // Фильтрация по Board (например, по номеру)
                                (isDeleted == null || boardseat.isDeleted() == isDeleted))
                .sorted((b1, b2) -> {
                    if ("seattype".equals(sortField)) {
                        return "asc".equals(sortDirection) ? b1.getSeattype().compareTo(b2.getSeattype())
                                : b2.getSeattype().compareTo(b1.getSeattype());
                    } else if ("rownumber".equals(sortField)) {
                        return "asc".equals(sortDirection) ? b1.getRownumber().compareTo(b2.getRownumber())
                                : b2.getRownumber().compareTo(b1.getRownumber());
                    }
                    return 0;
                })
                .collect(Collectors.toList());

        int start = Math.min((int) pageable.getOffset(), filteredBoardSeats.size());
        int end = Math.min(start + pageable.getPageSize(), filteredBoardSeats.size());

        return new PageImpl<>(filteredBoardSeats.subList(start, end), pageable, filteredBoardSeats.size());
    }

    Optional<Boardseat> findById(Integer id);

    Boardseat save(Boardseat boardseat);

    Boardseat saveAndFlush(Boardseat boardseat);

    List<Boardseat> findAll();
}
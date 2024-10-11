package sakura.arachnida.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sakura.arachnida.models.Board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public interface BoardRepository extends JpaRepository<Board, UUID> {

    default Page<Board> findAllWithPaginationAndFilters(
            Pageable pageable,
            String filterBoardNumber,
            String filterYear,
            Boolean isDeleted,
            String sortField,
            String sortDirection) {

        List<Board> allBoards = findAll();

        List<Board> filteredBoards = allBoards.stream()
                .filter(board -> (filterBoardNumber == null || board.getBoardnumber().contains(filterBoardNumber)) &&
                        (filterYear == null || board.getYear().toString().contains(filterYear)) &&
                        (isDeleted == null || board.isDeleted() == isDeleted))
                .sorted((b1, b2) -> {
                    if ("boardnumber".equals(sortField)) {
                        return "asc".equals(sortDirection) ? b1.getBoardnumber().compareTo(b2.getBoardnumber())
                                : b2.getBoardnumber().compareTo(b1.getBoardnumber());
                    } else if ("year".equals(sortField)) {
                        return "asc".equals(sortDirection) ? b1.getYear().compareTo(b2.getYear())
                                : b2.getYear().compareTo(b1.getYear());
                    }
                    return 0;
                })
                .collect(Collectors.toList());

        int start = Math.min((int) pageable.getOffset(), filteredBoards.size());
        int end = Math.min(start + pageable.getPageSize(), filteredBoards.size());

        return new PageImpl<>(filteredBoards.subList(start, end), pageable, filteredBoards.size());
    }

    Board findById(Integer id);
    Board save(Board board);
    Board saveAndFlush(Board board);
    List<Board> findAll();
}
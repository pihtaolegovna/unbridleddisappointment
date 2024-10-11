package sakura.arachnida.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sakura.arachnida.models.Board;

import java.util.List;
import java.util.UUID;

public interface BoardService {
    List<Board> findAll();
    Board create(Board board);
    Board update(Board board);
    Board findById(UUID id);
    Board findByOkId(Integer id);
    void deleteById(Integer id);

    void deleteById(UUID id);

    Page<Board> findAllWithPagination(Pageable pageable);
    Page<Board> findAllWithPaginationAndFilters(Pageable pageable, String filterBoardNumber, String filterYear, Boolean isDeleted, String sortField, String sortDirection);
    void logicDelete(List<UUID> ids);
    void delete(List<UUID> ids);
}
package sakura.arachnida.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sakura.arachnida.models.Board;
import sakura.arachnida.repository.BoardRepository;

import java.util.List;
import java.util.UUID;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Override
    public Board create(Board board) {
        return boardRepository.save(board);
    }

    @Override
    public Board update(Board board) {
        return boardRepository.save(board);
    }

    @Override
    public Board findById(UUID id) {
        return boardRepository.findById(id).orElse(null);
    }

    public Board findByOkId(Integer id) {
        return boardRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public void deleteById(UUID id) {
        boardRepository.deleteById(id);
    }

    @Override
    public Page<Board> findAllWithPagination(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Override
    public Page<Board> findAllWithPaginationAndFilters(Pageable pageable, String filterBoardNumber, String filterYear, Boolean isDeleted, String sortField, String sortDirection) {
        return boardRepository.findAllWithPaginationAndFilters(pageable, filterBoardNumber, filterYear, isDeleted, sortField, sortDirection);
    }

    @Override
    public void logicDelete(List<UUID> ids) {
        for (UUID id : ids) {
            Board board = findById(id);
            if (board != null) {
                board.setIsDeleted(!(board.isDeleted()));
                boardRepository.save(board);
            }
        }
    }

    @Override
    public void delete(List<UUID> ids) {
        for (UUID id : ids) {
            deleteById(id);
        }
    }
}
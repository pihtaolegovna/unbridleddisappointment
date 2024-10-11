package sakura.arachnida.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sakura.arachnida.models.Boardseat;
import sakura.arachnida.repository.BoardseatRepository;

import java.util.List;

@Service
public class BoardseatServiceImpl implements BoardseatService {

    private final BoardseatRepository boardseatRepository;

    @Autowired
    public BoardseatServiceImpl(BoardseatRepository boardseatRepository) {
        this.boardseatRepository = boardseatRepository;
    }

    @Override
    public List<Boardseat> findAll() {
        return boardseatRepository.findAll();
    }

    @Override
    public Boardseat create(Boardseat boardseat) {
        return boardseatRepository.save(boardseat);
    }

    @Override
    public Boardseat update(Boardseat boardseat) {
        return boardseatRepository.save(boardseat);
    }

    @Override
    public Boardseat findById(Integer id) {
        return boardseatRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteById(Integer id) {
        boardseatRepository.deleteById(id);
        return false;
    }

    @Override
    public Page<Boardseat> findAllWithPagination(Pageable pageable) {
        return boardseatRepository.findAll(pageable);
    }

    @Override
    public Page<Boardseat> findAllWithPaginationAndFilters(Pageable pageable, String filterSeatType, String filterRowNumber, String filterBoard, Boolean isDeleted, String sortField, String sortDirection) {
        return boardseatRepository.findAllWithPaginationAndFilters(pageable, filterSeatType, filterRowNumber, filterBoard, isDeleted, sortField, sortDirection);
    }

    @Override
    public void logicDelete(List<Integer> ids) {
        for (Integer id : ids) {
            Boardseat boardseat = findById(id);
            if (boardseat != null) {
                boardseat.setIsDeleted(!(boardseat.isDeleted()));
                boardseatRepository.save(boardseat);
            }
        }
    }

    @Override
    public void delete(List<Integer> ids) {
        for (Integer id : ids) {
            deleteById(id);
        }
    }
}
package sakura.arachnida.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sakura.arachnida.models.Boardseat;

import java.util.List;

public interface BoardseatService {
    List<Boardseat> findAll();

    Boardseat create(Boardseat boardseat);

    Boardseat update(Boardseat boardseat);

    Boardseat findById(Integer id);

    boolean deleteById(Integer id);

    Page<Boardseat> findAllWithPagination(Pageable pageable);

    Page<Boardseat> findAllWithPaginationAndFilters(Pageable pageable, String filterSeatType, String filterRowNumber, String filterBoard, Boolean isDeleted, String sortField, String sortDirection);

    void logicDelete(List<Integer> ids);

    void delete(List<Integer> ids);
}
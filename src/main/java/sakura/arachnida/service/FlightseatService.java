package sakura.arachnida.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sakura.arachnida.models.Flightseat;

import java.util.List;
import java.util.UUID;

public interface FlightseatService {
    List<Flightseat> findAll();

    Flightseat create(Flightseat flightseat);

    Flightseat update(Flightseat flightseat);

    Flightseat findById(Integer id);

    void deleteById(Integer id);

    Page<Flightseat> findAllWithPagination(Pageable pageable);

    Page<Flightseat> findAllWithPaginationAndFilters(Pageable pageable, String filterStatus, Double filterPrice, Boolean isDeleted, String sortField, String sortDirection);

    void logicDelete(List<Integer> ids);

    void delete(List<Integer> ids);

    void restore(Long id);
}
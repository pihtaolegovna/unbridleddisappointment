package sakura.arachnida.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sakura.arachnida.models.Flight;

import java.util.List;
import java.util.UUID;

public interface FlightService {
    List<Flight> findAll();
    Flight create(Flight flight);
    Flight update(Flight flight);
    Flight findById(Integer id);
    void deleteById(Integer id);
    Page<Flight> findAllWithPagination(Pageable pageable);
    Page<Flight> findAllWithPaginationAndFilters(Pageable pageable, String filterDeparturePlace, String filterArrivalPlace, Boolean isDeleted, String sortField, String sortDirection);
    void logicDelete(List<Integer> ids);
    void delete(List<Integer> ids);
}
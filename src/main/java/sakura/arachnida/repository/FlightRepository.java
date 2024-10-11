package sakura.arachnida.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sakura.arachnida.models.Flight;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {

    default Page<Flight> findAllWithPaginationAndFilters(
            Pageable pageable,
            String filterDeparturePlace,
            String filterArrivalPlace,
            Boolean isDeleted,
            String sortField,
            String sortDirection) {

        List<Flight> allFlights = findAll();

        List<Flight> filteredFlights = allFlights.stream()
                .filter(flight -> (filterDeparturePlace == null || flight.getDepartureplace().contains(filterDeparturePlace)) &&
                        (filterArrivalPlace == null || flight.getArrivalplace().contains(filterArrivalPlace)) &&
                        (isDeleted == null || flight.isDeleted() == isDeleted))
                .sorted((f1, f2) -> {
                    if ("departureplace".equals(sortField)) {
                        return "asc".equals(sortDirection) ? f1.getDepartureplace().compareTo(f2.getDepartureplace())
                                : f2.getDepartureplace().compareTo(f1.getDepartureplace());
                    } else if ("arrivalplace".equals(sortField)) {
                        return "asc".equals(sortDirection) ? f1.getArrivalplace().compareTo(f2.getArrivalplace())
                                : f2.getArrivalplace().compareTo(f1.getArrivalplace());
                    }
                    return 0;
                })
                .collect(Collectors.toList());

        int start = Math.min((int) pageable.getOffset(), filteredFlights.size());
        int end = Math.min(start + pageable.getPageSize(), filteredFlights.size());

        return new PageImpl<>(filteredFlights.subList(start, end), pageable, filteredFlights.size());
    }

    Optional<Flight> findById(Integer id);
    Flight save(Flight flight);
    Flight saveAndFlush(Flight flight);
    List<Flight> findAll();
}
package sakura.arachnida.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sakura.arachnida.models.Flightseat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public interface FlightseatRepository extends JpaRepository<Flightseat, Integer> {

    default Page<Flightseat> findAllWithPaginationAndFilters(
            Pageable pageable,
            String filterStatus,
            Double filterPrice,
            Boolean isDeleted,
            String sortField,
            String sortDirection) {

        List<Flightseat> allFlightseats = findAll();

        List<Flightseat> filteredFlightseats = allFlightseats.stream()
                .filter(flightseat -> (filterStatus == null || flightseat.getStatus().contains(filterStatus)) &&
                        (filterPrice == null || flightseat.getPrice().equals(filterPrice)) &&
                        (isDeleted == null || flightseat.isDeleted() == isDeleted))
                .sorted((f1, f2) -> {
                    if ("status".equals(sortField)) {
                        return "asc".equals(sortDirection) ? f1.getStatus().compareTo(f2.getStatus())
                                : f2.getStatus().compareTo(f1.getStatus());
                    } else if ("price".equals(sortField)) {
                        return "asc".equals(sortDirection) ? f1.getPrice().compareTo(f2.getPrice())
                                : f2.getPrice().compareTo(f1.getPrice());
                    }
                    return 0;
                })
                .collect(Collectors.toList());

        int start = Math.min((int) pageable.getOffset(), filteredFlightseats.size());
        int end = Math.min(start + pageable.getPageSize(), filteredFlightseats.size());

        return new PageImpl<>(filteredFlightseats.subList(start, end), pageable, filteredFlightseats.size());
    }

    Optional<Flightseat> findById(Integer id);

    Flightseat save(Flightseat flightseat);

    Flightseat saveAndFlush(Flightseat flightseat);


    List<Flightseat> findAll();
}
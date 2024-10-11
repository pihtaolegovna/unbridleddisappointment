package sakura.arachnida.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sakura.arachnida.models.Manufacturer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {

    default Page<Manufacturer> findAllWithPaginationAndFilters(
            Pageable pageable,
            String filterName,
            Boolean isDeleted,
            String sortField,
            String sortDirection) {

        List<Manufacturer> allManufacturers = findAll();

        List<Manufacturer> filteredManufacturers = allManufacturers.stream()
                .filter(manufacturer -> (filterName == null || manufacturer.getName().contains(filterName)) &&
                        (isDeleted == null || manufacturer.isDeleted() == isDeleted))
                .sorted((m1, m2) -> {
                    if ("name".equals(sortField)) {
                        return "asc".equals(sortDirection) ? m1.getName().compareTo(m2.getName())
                                : m2.getName().compareTo(m1.getName());
                    }
                    return 0;
                })
                .collect(Collectors.toList());

        int start = Math.min((int) pageable.getOffset(), filteredManufacturers.size());
        int end = Math.min(start + pageable.getPageSize(), filteredManufacturers.size());

        return new PageImpl<>(filteredManufacturers.subList(start, end), pageable, filteredManufacturers.size());
    }

    Optional<Manufacturer> findById(Integer id);

    Manufacturer save(Manufacturer manufacturer);

    Manufacturer saveAndFlush(Manufacturer manufacturer);

    List<Manufacturer> findAll();
}
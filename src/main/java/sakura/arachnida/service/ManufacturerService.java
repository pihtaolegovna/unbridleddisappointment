package sakura.arachnida.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sakura.arachnida.models.Manufacturer;

import java.util.List;

public interface ManufacturerService {
    List<Manufacturer> findAll();
    Manufacturer create(Manufacturer manufacturer);
    Manufacturer update(Manufacturer manufacturer);
    Manufacturer findById(Integer id);
    void deleteById(Integer id);
    Page<Manufacturer> findAllWithPagination(Pageable pageable);
    Page<Manufacturer> findAllWithPaginationAndFilters(Pageable pageable, String filterName, Boolean isDeleted, String sortField, String sortDirection);
    void logicDelete(List<Integer> ids);
    void delete(List<Integer> ids);
}
package sakura.arachnida.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sakura.arachnida.models.Model;

import java.util.List;

public interface ModelService {
    List<Model> findAll();

    Model create(Model model);

    Model update(Model model);

    Model findById(Integer id);

    void deleteById(Integer id);

    Page<Model> findAllWithPagination(Pageable pageable);

    Page<Model> findAllWithPaginationAndFilters(Pageable pageable, String filterName, Boolean isDeleted, String sortField, String sortDirection);

    void logicDelete(List<Integer> ids);

    void delete(List<Integer> ids);
}
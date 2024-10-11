package sakura.arachnida.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sakura.arachnida.models.Model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {

    default Page<Model> findAllWithPaginationAndFilters(
            Pageable pageable,
            String filterName,
            Boolean isDeleted,
            String sortField,
            String sortDirection) {

        List<Model> allModels = findAll();

        List<Model> filteredModels = allModels.stream()
                .filter(model -> (filterName == null || model.getName().contains(filterName)) &&
                        (isDeleted == null || model.isDeleted() == isDeleted))
                .sorted((m1, m2) -> {
                    if ("name".equals(sortField)) {
                        return "asc".equals(sortDirection) ? m1.getName().compareTo(m2.getName())
                                : m2.getName().compareTo(m1.getName());
                    }
                    return 0;
                })
                .collect(Collectors.toList());

        int start = Math.min((int) pageable.getOffset(), filteredModels.size());
        int end = Math.min(start + pageable.getPageSize(), filteredModels.size());

        return new PageImpl<>(filteredModels.subList(start, end), pageable, filteredModels.size());
    }

    Optional<Model> findById(Integer id);

    Model save(Model model);

    Model saveAndFlush(Model model);

    List<Model> findAll();
}
package sakura.arachnida.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sakura.arachnida.models.Employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    default Page<Employee> findAllWithPaginationAndFilters(
            Pageable pageable,
            String filterPassword,
            Boolean isDeleted,
            String sortField,
            String sortDirection) {

        List<Employee> allEmployees = findAll();

        List<Employee> filteredEmployees = allEmployees.stream()
                .filter(employee -> (filterPassword == null || employee.getPassword().contains(filterPassword)) &&
                        (isDeleted == null || employee.isDeleted() == isDeleted))
                .sorted((e1, e2) -> {
                    if ("password".equals(sortField)) {
                        return "asc".equals(sortDirection) ? e1.getPassword().compareTo(e2.getPassword())
                                : e2.getPassword().compareTo(e1.getPassword());
                    }
                    return 0;
                })
                .collect(Collectors.toList());

        int start = Math.min((int) pageable.getOffset(), filteredEmployees.size());
        int end = Math.min(start + pageable.getPageSize(), filteredEmployees.size());

        return new PageImpl<>(filteredEmployees.subList(start, end), pageable, filteredEmployees.size());
    }

    Optional<Employee> findById(Integer id);

    Employee save(Employee employee);

    Employee saveAndFlush(Employee employee);


    List<Employee> findAll();
}
package sakura.arachnida.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sakura.arachnida.models.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
    Employee create(Employee employee);
    Employee update(Employee employee);
    Employee findById(Integer id);
    void deleteById(Integer id);
    Page<Employee> findAllWithPagination(Pageable pageable);
    Page<Employee> findAllWithPaginationAndFilters(Pageable pageable, String filterPassword, Boolean isDeleted, String sortField, String sortDirection);
    void logicDelete(List<Integer> ids);
    void delete(List<Integer> ids);
}
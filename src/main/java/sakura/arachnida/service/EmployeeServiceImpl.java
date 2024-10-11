package sakura.arachnida.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sakura.arachnida.models.Employee;
import sakura.arachnida.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee findById(Integer id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Page<Employee> findAllWithPagination(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Override
    public Page<Employee> findAllWithPaginationAndFilters(Pageable pageable, String filterPassword, Boolean isDeleted, String sortField, String sortDirection) {
        return employeeRepository.findAllWithPaginationAndFilters(pageable, filterPassword, isDeleted, sortField, sortDirection);
    }

    @Override
    public void logicDelete(List<Integer> ids) {
        for (Integer id : ids) {
            Employee employee = findById(id);
            if (employee != null) {
                employee.setIsDeleted(!(employee.isDeleted()));
                employeeRepository.save(employee);
            }
        }
    }

    @Override
    public void delete(List<Integer> ids) {
        for (Integer id : ids) {
            deleteById(id);
        }
    }
}
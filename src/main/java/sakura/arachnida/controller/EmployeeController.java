package sakura.arachnida.controller;

import sakura.arachnida.models.Employee;
import sakura.arachnida.models.User;
import sakura.arachnida.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String filterPassword,
            @RequestParam(required = false) Boolean isDeleted,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employeePage = employeeService.findAllWithPaginationAndFilters(
                pageable, filterPassword, isDeleted, sortField, sortDirection);

        model.addAttribute("employees", employeePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", employeePage.getTotalPages());
        return "employeePage";
    }

    @GetMapping("/create")
    public String getCreateEmployee() {
        return "createEmployee";
    }

    @PostMapping("/create")
    public String postCreateEmployee(
            @RequestParam(name = "user") User user,
            @RequestParam(name = "password") String password
    ) {
        Employee newEmployee = new Employee();
        newEmployee.setUser(user);
        newEmployee.setPassword(password);
        employeeService.create(newEmployee);
        return "redirect:/employee";
    }

    @GetMapping("/edit/{id}")
    public String getEditEmployee(@PathVariable Integer id, Model model) {
        var employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        return "editEmployee";
    }

    @PostMapping("/edit/{id}")
    public String postEditEmployee(
            @PathVariable Integer id,
            @RequestParam(name = "user") User user,
            @RequestParam(name = "password") String password
    ) {
        var employee = new Employee();
        employee.setId(id);
        employee.setUser(user);
        employee.setPassword(password);
        employeeService.update(employee);
        return "redirect:/employee";
    }

    @PostMapping("/remove")
    public String logicDeleteEmployees(@RequestParam List<Integer> ids) {
        employeeService.logicDelete(ids);
        return "redirect:/employee";
    }

    @PostMapping("/delete")
    public String deleteEmployees(@RequestParam List<Integer> ids) {
        employeeService.delete(ids);
        return "redirect:/employee";
    }
}
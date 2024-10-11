package sakura.arachnida.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import sakura.arachnida.models.Ticket;
import sakura.arachnida.models.User;
import sakura.arachnida.repository.UserRepository;
import sakura.arachnida.service.FlightService;
import sakura.arachnida.service.FlightseatService;
import sakura.arachnida.service.TicketService;
import sakura.arachnida.service.UserService;
import sakura.arachnida.service.FlightseatService;

import java.util.List;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @Autowired
    private FlightService flightService;

    @Autowired
    private FlightseatService flightseatService;



    @GetMapping
    public String listTickets(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id") String sortField,
                              @RequestParam(defaultValue = "asc") String sortDirection,
                              @RequestParam(required = false) String filterStatus,
                              @RequestParam(required = false) Double filterPrice,
                              @RequestParam(required = false) Boolean isDeleted,
                              Model model) {



        Page<Ticket> tickets = ticketService.findAllWithPaginationAndFilters(page, size, sortField, sortDirection, filterStatus, filterPrice, isDeleted);

        model.addAttribute("tickets", tickets.getContent());
        model.addAttribute("currentPage", tickets.getNumber());
        model.addAttribute("totalPages", tickets.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("filterStatus", filterStatus);
        model.addAttribute("filterPrice", filterPrice);
        model.addAttribute("isDeleted", isDeleted);
        model.addAttribute("flightseats", flightseatService.findAll());
        model.addAttribute("users", userService.findAll()); // Предполагаем, что есть метод findAll в UserService
        model.addAttribute("flights", flightService.findAll()); // Предполагаем, что есть метод findAll в FlightService

        return "ticketPage"; // Название Thymeleaf шаблона
    }

    @PostMapping("/create")
    public String createTicket(@ModelAttribute Ticket ticket) {
        ticketService.create(ticket);
        return "redirect:/ticket";
    }

    @PostMapping("/edit")
    public String editTicket(@ModelAttribute Ticket ticket) {
        ticketService.update(ticket);
        return "redirect:/ticket";
    }

    @PostMapping("/delete")
    public String deleteTicket(@RequestParam("id") Integer id) {
        ticketService.logicDelete(List.of(id)); // Используем логическое удаление
        return "redirect:/ticket";
    }

    @PostMapping("/restore")
    public String restoreTicket(@RequestParam("id") Integer id) {
        ticketService.restore(List.of(id)); // Восстановление
        return "redirect:/ticket";
    }

    @PostMapping("/deleteSelected")
    public String deleteSelectedTickets(@RequestParam("ids") List<Integer> ids) {
        ticketService.logicDelete(ids);
        return "redirect:/ticket";
    }
}
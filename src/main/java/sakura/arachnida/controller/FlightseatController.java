package sakura.arachnida.controller;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sakura.arachnida.models.Flightseat;
import sakura.arachnida.models.Boardseat; // Import the Boardseat model
import sakura.arachnida.models.Flight; // Import the Flight model
import sakura.arachnida.service.FlightseatService;
import sakura.arachnida.service.BoardseatService; // Import BoardseatService
import sakura.arachnida.service.FlightService; // Import FlightService
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import sakura.arachnida.service.FlightseatServiceImpl;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/flightseat")
public class FlightseatController {

    private final FlightseatService flightseatService;
    private final BoardseatService boardseatService; // Service for Boardseat
    private final FlightService flightService; // Service for Flight
    private final FlightseatServiceImpl flightseatServiceImpl;

    public FlightseatController(FlightseatService flightseatService, BoardseatService boardseatService, FlightService flightService, FlightseatServiceImpl flightseatServiceImpl) {
        this.flightseatService = flightseatService;
        this.boardseatService = boardseatService; // Initialize BoardseatService
        this.flightService = flightService; // Initialize FlightService
        this.flightseatServiceImpl = flightseatServiceImpl;
    }

    @GetMapping
    public String getAllFlightseats(
            @RequestParam(required = false) String filterStatus,
            @RequestParam(required = false) Double filterPrice,
            @RequestParam(required = false) Boolean isDeleted,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "status") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            Model model) {

        // Ensure isDeleted defaults to false if not provided
        if (isDeleted == null) {
            isDeleted = false;
        }

        // Fetch flight seats with pagination and filters
        Pageable pageable = PageRequest.of(page, size);
        Page<Flightseat> flightseatPage = flightseatService.findAllWithPaginationAndFilters(
                pageable, filterStatus, filterPrice, isDeleted, sortField, sortDirection);

        // Add attributes to the model
        model.addAttribute("flightseats", flightseatPage.getContent());
        model.addAttribute("currentPage", flightseatPage.getNumber());
        model.addAttribute("totalPages", flightseatPage.getTotalPages());
        model.addAttribute("boardseats", boardseatService.findAll());
        model.addAttribute("flights", flightService.findAll());
        model.addAttribute("filterStatus", filterStatus);
        model.addAttribute("filterPrice", filterPrice);
        model.addAttribute("isDeleted", isDeleted); // Add this line to set isDeleted

        return "flightseatPage"; // Return the view name
    }

    @PostMapping("/create")
    public String postCreateFlightseat(@ModelAttribute Flightseat newFlightseat) {
        flightseatService.create(newFlightseat);
        return "redirect:/flightseat"; // Redirect to the flight seat list
    }

    @PostMapping("/edit")
    public String postEditFlightseat(@ModelAttribute Flightseat flightseat) {
        flightseatService.update(flightseat);
        return "redirect:/flightseat"; // Redirect to the flight seat list
    }

    @PostMapping("/remove")
    public String logicDeleteFlightseats(@RequestParam List<Integer> ids) {
        flightseatService.logicDelete(ids);
        return "redirect:/flightseat"; // Redirect to the flight seat list
    }

    @PostMapping("/delete")
    public String deleteFlightSeat(@RequestParam Integer id) {
        flightseatService.delete(Collections.singletonList(id));
        return "redirect:/flightseat";
    }

    @PostMapping("/deleteSelected")
    public String deleteSelected(@RequestParam("ids") List<Integer> ids, RedirectAttributes redirectAttributes) {
        if (ids != null && !ids.isEmpty()) {
            flightseatService.logicDelete(ids); // Perform logical delete
            redirectAttributes.addFlashAttribute("message", "Selected flight seats have been deleted.");
        } else {
            redirectAttributes.addFlashAttribute("error", "No flight seats were selected.");
        }
        return "redirect:/flightseat"; // Redirect back to the main page
    }

    @PostMapping("/restore")
    public String restoreFlightSeat(@RequestParam Long id) {
        flightseatService.restore(id);
        return "redirect:/flightseat";
    }
}
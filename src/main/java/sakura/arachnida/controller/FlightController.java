package sakura.arachnida.controller;

import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import sakura.arachnida.models.Flight;
import sakura.arachnida.repository.BoardRepository;
import sakura.arachnida.service.BoardService;
import sakura.arachnida.service.FlightService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;

@Controller
@RequestMapping("/flight")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;

    @GetMapping
    public String getAllFlights(
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false) String filterDeparturePlace,
            @RequestParam(required = false) String filterArrivalPlace,
            @RequestParam(required = false) Boolean isDeleted,
            @RequestParam(defaultValue = "departureplace") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            Model model) {

        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Direction.fromString(sortDirection), sortField));

        Page<Flight> flightPage = flightService.findAllWithPaginationAndFilters(
                pageable, filterDeparturePlace, filterArrivalPlace, isDeleted, sortField, sortDirection);

        // Add model attributes for the view
        model.addAttribute("flights", flightPage.getContent());
        model.addAttribute("currentPage", flightPage.getNumber());
        model.addAttribute("totalPages", flightPage.getTotalPages());
        model.addAttribute("filterDeparturePlace", filterDeparturePlace);
        model.addAttribute("filterArrivalPlace", filterArrivalPlace);
        model.addAttribute("isDeleted", isDeleted);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);

        model.addAttribute("boards", boardService.findAll());

        return "flightPage";
    }


    @GetMapping("/create")
    public String getCreateFlight() {
        return "createFlight";
    }

    @PostMapping("/create")
    public String postCreateFlight(
            @RequestParam(name = "departuretime") String departuretime,
            @RequestParam(name = "arrivaltime") String arrivaltime,
            @RequestParam(name = "departureplace") String departureplace,
            @RequestParam(name = "arrivalplace") String arrivalplace,
            @RequestParam(name = "boardselect") Integer boardnumber
    ) {
        Flight newFlight = new Flight();
        newFlight.setDeparturetime(departuretime);
        newFlight.setArrivaltime(arrivaltime);
        newFlight.setDepartureplace(departureplace);
        newFlight.setArrivalplace(arrivalplace);
        newFlight.setBoard(boardRepository.findById(boardnumber));
        flightService.create(newFlight);
        return "redirect:/flight";
    }

    @GetMapping("/edit/{id}")
    public String getEditFlight(@PathVariable Integer id, Model model) {
        var flight = flightService.findById(id);
        model.addAttribute("flight", flight);
        return "editFlight";
    }

    @PostMapping("/edit/{id}")
    public String postEditFlight(
            @PathVariable Integer id,
            @RequestParam(name = "departuretime") String departuretime,
            @RequestParam(name = "arrivaltime") String arrivaltime,
            @RequestParam(name = "departureplace") String departureplace,
            @RequestParam(name = "arrivalplace") String arrivalplace,
            @RequestParam(name = "boardselect") Integer boardnumber
    ) {
        var flight = new Flight();
        flight.setId(id);
        flight.setDeparturetime(departuretime);
        flight.setArrivaltime(arrivaltime);
        flight.setDepartureplace(departureplace);
        flight.setArrivalplace(arrivalplace);
        flight.setBoard(boardRepository.findById(boardnumber));
        flightService.update(flight);
        return "redirect:/flight";
    }

    @PostMapping("/remove")
    public String logicDeleteFlights(@RequestParam List<Integer> ids) {
        flightService.logicDelete(ids);
        return "redirect:/flight";
    }

    @PostMapping("/delete")
    public String deleteFlights(@RequestParam List<Integer> ids) {
        flightService.delete(ids);
        return "redirect:/flight";
    }
}
package sakura.arachnida.controller;

import sakura.arachnida.models.Manufacturer;
import sakura.arachnida.service.ManufacturerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Controller
@RequestMapping("/manufacturer")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public String getAllManufacturers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String filterName,
            @RequestParam(required = false) Boolean isDeleted,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Manufacturer> manufacturerPage = manufacturerService.findAllWithPaginationAndFilters(
                pageable, filterName, isDeleted, sortField, sortDirection);

        model.addAttribute("manufacturers", manufacturerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", manufacturerPage.getTotalPages());
        return "manufacturerPage";
    }

    @GetMapping("/create")
    public String getCreateManufacturer() {
        return "createManufacturer";
    }

    @PostMapping("/create")
    public String postCreateManufacturer(
            @RequestParam(name = "name") String name
    ) {
        Manufacturer newManufacturer = new Manufacturer();
        newManufacturer.setName(name);
        manufacturerService.create(newManufacturer);
        return "redirect:/manufacturer";
    }

    @GetMapping("/edit/{id}")
    public String getEditManufacturer(@PathVariable Integer id, Model model) {
        var manufacturer = manufacturerService.findById(id);
        model.addAttribute("manufacturer", manufacturer);
        return "editManufacturer";
    }

    @PostMapping("/edit/{id}")
    public String postEditManufacturer(
            @PathVariable Integer id,
            @RequestParam(name = "name") String name
    ) {
        var manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturerService.update(manufacturer);
        return "redirect:/manufacturer";
    }

    @PostMapping("/remove")
    public String logicDeleteManufacturers(@RequestParam List<Integer> ids) {
        manufacturerService.logicDelete(ids);
        return "redirect:/manufacturer";
    }

    @PostMapping("/delete")
    public String deleteManufacturers(@RequestParam List<Integer> ids) {
        manufacturerService.delete(ids);
        return "redirect:/manufacturer";
    }
}
package sakura.arachnida.controller;

import sakura.arachnida.models.Manufacturer;
import sakura.arachnida.service.ManufacturerService;
import sakura.arachnida.service.ModelService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/model")
public class ModelController {

    private final ModelService modelService;
    private final ManufacturerService manufacturerService; // Add ManufacturerService

    public ModelController(ModelService modelService, ManufacturerService manufacturerService) {
        this.modelService = modelService;
        this.manufacturerService = manufacturerService; // Initialize ManufacturerService
    }


    @GetMapping
    public String getAllModels(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String filterName,
            @RequestParam(required = false) Boolean isDeleted,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<sakura.arachnida.models.Model> modelPage = modelService.findAllWithPaginationAndFilters(
                pageable, filterName, isDeleted, sortField, sortDirection);

        model.addAttribute("models", modelPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", modelPage.getTotalPages());

        List<Manufacturer> manufacturers = manufacturerService.findAll(); // Fetch manufacturers
        model.addAttribute("manufacturers", manufacturers); // Add to model

        return "modelPage";
    }

    @GetMapping("/create")
    public String getCreateModel() {
        return "createModel";
    }

    @PostMapping("/create")
    public String postCreateModel(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "manufacturer", required = false) Integer manufacturerId
    ) {
        sakura.arachnida.models.Model newModel = new sakura.arachnida.models.Model();
        newModel.setName(name);
        if (manufacturerId != null) {
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(manufacturerId);
            newModel.setManufacturer(manufacturer);
        }
        modelService.create(newModel);
        return "redirect:/model";
    }

    @GetMapping("/edit/{id}")
    public String getEditModel(@PathVariable Integer id, Model model) {
        var modelEntity = modelService.findById(id);
        model.addAttribute("model", modelEntity);
        return "editModel";
    }

    @PostMapping("/edit/{id}")
    public String postEditModel(
            @PathVariable Integer id,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "manufacturer", required = false) Integer manufacturerId
    ) {
        var modelEntity = new sakura.arachnida.models.Model();
        modelEntity.setId(id);
        modelEntity.setName(name);
        if (manufacturerId != null) {
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(manufacturerId);
            modelEntity.setManufacturer(manufacturer);
        }
        modelService.update(modelEntity);
        return "redirect:/model";
    }

    @PostMapping("/remove")
    public String logicDeleteModels(@RequestParam List<Integer> ids) {
        modelService.logicDelete(ids);
        return "redirect:/model";
    }

    @PostMapping("/delete")
    public String deleteModels(@RequestParam List<Integer> ids) {
        modelService.delete(ids);
        return "redirect:/model";
    }
}
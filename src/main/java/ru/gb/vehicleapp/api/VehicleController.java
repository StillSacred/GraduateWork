package ru.gb.vehicleapp.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.vehicleapp.api.request.SearchRequest;
import ru.gb.vehicleapp.api.request.VehicleRequest;
import ru.gb.vehicleapp.service.interfaces.VehicleService;
import ru.gb.vehicleapp.service.response.VehicleResponse;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/vehicle")
public class VehicleController {

    private final VehicleService service;

    @GetMapping
    public String getMainPaige(Model model, @RequestParam(value = "offset", defaultValue = "0") int offset,
                               @RequestParam(value = "limit", defaultValue = "5") int limit) {
        Page<VehicleResponse> page = service.findAll(PageRequest.of(offset, limit));
        model.addAttribute("page", page);
        model.addAttribute("pageNumber", page.getNumber());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("limit", limit);
        model.addAttribute("request", new VehicleRequest());
        return "index";
    }

    @GetMapping("/filter")
    public String filter(Model model, SearchRequest request, @RequestParam(value = "offset", defaultValue = "0") int offset,
                         @RequestParam(value = "limit", defaultValue = "5") int limit) {
        Page<VehicleResponse> page = service.findAllByRequest(request, PageRequest.of(offset, limit));
        model.addAttribute("page", page);
        model.addAttribute("pageNumber", page.getNumber());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("limit", limit);
        model.addAttribute("request", new VehicleRequest());
        return "index";
    }

    @GetMapping("/new")
    public String getCreationPage(Model model) {
        model.addAttribute("request", new VehicleRequest());
        model.addAttribute("types", service.getTypes());
        return "new-vehicle";
    }

    @PostMapping("/new")
    public String createVehicle(@Valid VehicleRequest request) {
        service.create(request);
        return "redirect:/vehicle";
    }

    @GetMapping("/update/{regNumber}")
    public String getUpdatePage(@PathVariable String regNumber, Model model) {
        model.addAttribute("request", new VehicleRequest());
        model.addAttribute("vehicle", service.findByRegNumber(regNumber));
        model.addAttribute("oldRegNumber", regNumber);
        model.addAttribute("types", service.getTypes());
        return "update-vehicle";
    }

    @PatchMapping("/update/{regNumber}")
    public String updateVehicle(@PathVariable String regNumber, @Valid VehicleRequest request) {
        service.update(request, regNumber);
        return "redirect:/vehicle";
    }

    @DeleteMapping("/delete/{regNumber}")
    public String deleteVehicle(@PathVariable String regNumber) {
        service.delete(regNumber);
        return "redirect:/vehicle";
    }
}
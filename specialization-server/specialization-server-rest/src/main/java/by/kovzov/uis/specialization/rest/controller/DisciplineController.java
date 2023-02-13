package by.kovzov.uis.specialization.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.kovzov.uis.specialization.dto.DisciplineDto;
import by.kovzov.uis.specialization.service.api.DisciplineService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/disciplines")
@AllArgsConstructor
public class DisciplineController {

    private final DisciplineService disciplineService;

    @GetMapping("/{id}")
    public DisciplineDto getById(@PathVariable Long id) {
        return disciplineService.getById(id);
    }
}

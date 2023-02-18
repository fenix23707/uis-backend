package by.kovzov.uis.specialization.rest.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import by.kovzov.uis.specialization.dto.DisciplineDto;
import by.kovzov.uis.specialization.service.api.DisciplineService;
import jakarta.validation.Valid;
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

    @GetMapping("/search")
    public Page<DisciplineDto> search(@RequestParam String query,
                                      @PageableDefault(sort = "name", direction = Direction.ASC) Pageable pageable ) {
        return disciplineService.search(query, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DisciplineDto create(@RequestBody @Valid DisciplineDto disciplineDto) {
        return disciplineService.create(disciplineDto);
    }
}

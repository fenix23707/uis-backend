package by.kovzov.uis.academic.rest.controller;

import by.kovzov.uis.academic.dto.DisciplineDto;
import by.kovzov.uis.academic.service.api.DisciplineService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/disciplines")
@AllArgsConstructor
public class DisciplineController {

    private final DisciplineService disciplineService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('DISCIPLINE_READ') or hasAnyAuthority('DISCIPLINE_UPDATE')")
    public DisciplineDto getById(@PathVariable Long id) {
        return disciplineService.getById(id);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('DISCIPLINE_SEARCH') or hasAnyAuthority('CURRICULUM_UPDATE')")
    public Page<DisciplineDto> search(@RequestParam String query,
                                      @PageableDefault(sort = "name", direction = Direction.ASC) Pageable pageable ) {
        return disciplineService.search(query, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('DISCIPLINE_CREATE')")
    public DisciplineDto create(@RequestBody @Valid DisciplineDto disciplineDto) {
        return disciplineService.create(disciplineDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('DISCIPLINE_UPDATE')")
    public DisciplineDto update(@PathVariable Long id,
                                @RequestBody @Valid DisciplineDto disciplineDto) {
        return disciplineService.update(id, disciplineDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('DISCIPLINE_DELETE')")
    public void delete(@PathVariable Long id) {
        disciplineService.deleteById(id);
    }
}

package by.kovzov.uis.academic.rest.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import by.kovzov.uis.academic.dto.SpecializationDto;
import by.kovzov.uis.academic.dto.SpecializationRequestDto;
import by.kovzov.uis.academic.service.api.SpecializationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

// Help https://www.baeldung.com/spring-data-web-support
@RestController
@RequestMapping("/api/specializations")
@AllArgsConstructor
public class SpecializationController {

    private final SpecializationService specializationService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SPECIALIZATION_GET')")
    public SpecializationDto getById(@PathVariable Long id) {
        return specializationService.getById(id);
    }

    @GetMapping("/parents")
    @PreAuthorize("hasAuthority('SPECIALIZATION_GET')")
    public Page<SpecializationDto> getAllParents(@PageableDefault(sort = "name", direction = Direction.ASC) Pageable pageable) {
        return specializationService.getAllParents(pageable);
    }

    @GetMapping("/{parentId}/children")
    @PreAuthorize("hasAuthority('SPECIALIZATION_GET')")
    public List<SpecializationDto> getAllChildren(@PathVariable Long parentId, @SortDefault("name") Sort sort) {
        return specializationService.getAllChildrenByParentId(parentId, sort);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('SPECIALIZATION_SEARCH')")
    public Page<SpecializationDto> search(@RequestParam String query,
                                          @PageableDefault(sort = "name", direction = Direction.ASC) Pageable pageable) {
        return specializationService.search(query, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('SPECIALIZATION_CREATE')")
    public SpecializationDto create(@RequestBody @Valid SpecializationRequestDto specializationRequestDto) {
        return specializationService.create(specializationRequestDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SPECIALIZATION_UPDATE')")
    public SpecializationDto update(@PathVariable Long id,
                                    @RequestBody @Valid SpecializationRequestDto specializationRequestDto) {
        return specializationService.update(id, specializationRequestDto);
    }
}

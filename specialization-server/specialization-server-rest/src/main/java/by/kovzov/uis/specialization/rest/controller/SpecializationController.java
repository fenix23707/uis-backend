package by.kovzov.uis.specialization.rest.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
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

import by.kovzov.uis.specialization.dto.SpecializationDto;
import by.kovzov.uis.specialization.dto.SpecializationRequestDto;
import by.kovzov.uis.specialization.service.api.SpecializationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

// Help https://www.baeldung.com/spring-data-web-support
@RestController
@RequestMapping("/api/specializations")
@AllArgsConstructor
public class SpecializationController {

    private final SpecializationService specializationService;

    @GetMapping("/{id}")
    public SpecializationDto getById(@PathVariable Long id) {
        return specializationService.getById(id);
    }

    @GetMapping("/parents")
    public Page<SpecializationDto> getAllParents(@PageableDefault(sort = "name", direction = Direction.ASC) Pageable pageable) {
        return specializationService.getAllParents(pageable);
    }

    @GetMapping("/{parentId}/children")
    public List<SpecializationDto> getAllChildren(@PathVariable Long parentId, @SortDefault("name") Sort sort) {
        return specializationService.getAllChildrenByParentId(parentId, sort);
    }

    @GetMapping("/search")
    public Page<SpecializationDto> search(@RequestParam String query,
                                          @PageableDefault(sort = "name", direction = Direction.ASC) Pageable pageable) {
        return specializationService.search(query, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SpecializationDto create(@RequestBody @Valid SpecializationRequestDto specializationRequestDto) {
        return specializationService.create(specializationRequestDto);
    }

    @PutMapping("/{id}")
    public SpecializationDto update(@PathVariable Long id,
                                    @RequestBody @Valid SpecializationRequestDto specializationRequestDto) {
        return specializationService.update(id, specializationRequestDto);
    }
}

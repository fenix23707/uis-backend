package by.kovzov.uis.academic.rest.controller;

import java.util.List;

import by.kovzov.uis.academic.dto.CurriculumDisciplineDto;
import by.kovzov.uis.academic.service.api.CurriculumDisciplineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/curriculums")
@RequiredArgsConstructor
public class CurriculumDisciplineController {

    private final CurriculumDisciplineService curriculumDisciplineService;

    @GetMapping("/{curriculumId}/disciplines")
    @PreAuthorize("hasAuthority('CURRICULUM_GET')")
    public List<CurriculumDisciplineDto> getByCurriculumId(@PathVariable Long curriculumId,
                                                           @SortDefault("semester") Sort sort) {
        return curriculumDisciplineService.getAllByCurriculumId(curriculumId, sort);
    }

    @PostMapping("/disciplines")
    @PreAuthorize("hasAuthority('CURRICULUM_CREATE')")
    public CurriculumDisciplineDto create(@RequestBody @Valid CurriculumDisciplineDto dto) {
        return curriculumDisciplineService.create(dto);
    }
}

package by.kovzov.uis.academic.rest.controller;

import java.util.List;

import by.kovzov.uis.academic.dto.CurriculumDisciplineDto;
import by.kovzov.uis.academic.repository.entity.CurriculumDiscipline.CurriculumDisciplineId;
import by.kovzov.uis.academic.service.api.CurriculumDisciplineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/curriculums")
@RequiredArgsConstructor
public class CurriculumDisciplineController {

    private final CurriculumDisciplineService curriculumDisciplineService;

    @GetMapping("/{curriculumId}/disciplines")
    @PreAuthorize("hasAuthority('CURRICULUM_READ')")
    public List<CurriculumDisciplineDto> getByCurriculumId(@PathVariable Long curriculumId,
                                                           @SortDefault("semester") Sort sort) {
        return curriculumDisciplineService.getAllByCurriculumId(curriculumId, sort);
    }

    @PostMapping("/{curriculumId}/disciplines/{disciplineId}")
    @PreAuthorize("hasAuthority('CURRICULUM_CREATE')")
    public CurriculumDisciplineDto create(@PathVariable Long curriculumId,
                                          @PathVariable Long disciplineId,
                                          @RequestBody @Valid CurriculumDisciplineDto dto) {
        var id = new CurriculumDisciplineId(curriculumId, disciplineId);
        return curriculumDisciplineService.create(id, dto);
    }

    @PutMapping("/{curriculumId}/disciplines/{disciplineId}")
    @PreAuthorize("hasAuthority('CURRICULUM_UPDATE')")
    public CurriculumDisciplineDto update(@PathVariable Long curriculumId,
                                          @PathVariable Long disciplineId,
                                          @RequestBody @Valid CurriculumDisciplineDto dto) {
        var id = new CurriculumDisciplineId(curriculumId, disciplineId);
        return curriculumDisciplineService.update(id, dto);
    }

    @DeleteMapping("/{curriculumId}/disciplines/{disciplineId}")
    @PreAuthorize("hasAuthority('CURRICULUM_DELETE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long curriculumId,
                       @PathVariable Long disciplineId) {
        var id = new CurriculumDisciplineId(curriculumId, disciplineId);
        curriculumDisciplineService.delete(id);
    }
}

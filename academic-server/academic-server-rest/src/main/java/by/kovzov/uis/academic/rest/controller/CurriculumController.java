package by.kovzov.uis.academic.rest.controller;

import by.kovzov.uis.academic.dto.CurriculumDto;
import by.kovzov.uis.academic.dto.SearchDto;
import by.kovzov.uis.academic.service.api.CurriculumService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/curriculums")
@RequiredArgsConstructor
public class CurriculumController {

    private final CurriculumService curriculumService;

    @GetMapping()
    @PreAuthorize("hasAuthority('CURRICULUM_READ')")
    public Page<CurriculumDto> getAll(@PageableDefault(sort = "approvalDate", direction = Direction.DESC) Pageable pageable) {
        return curriculumService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CURRICULUM_READ')")
    public CurriculumDto getById(@PathVariable Long id) {
        return curriculumService.getById(id);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('CURRICULUM_SEARCH')")
    public Page<CurriculumDto> search(@RequestBody SearchDto searchDto,
                                      @PageableDefault(sort = "approvalDate", direction = Direction.DESC) Pageable pageable) {
        return curriculumService.search(searchDto, pageable);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CURRICULUM_CREATE')")
    public CurriculumDto create(@RequestBody @Valid CurriculumDto curriculumDto) {
        return curriculumService.create(curriculumDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CURRICULUM_UPDATE')")
    public CurriculumDto update(@PathVariable Long id,
                                @RequestBody @Valid CurriculumDto curriculumDto) {
        return curriculumService.update(id, curriculumDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('CURRICULUM_DELETE')")
    public void delete(@PathVariable Long id) {
        curriculumService.deleteById(id);
    }
}

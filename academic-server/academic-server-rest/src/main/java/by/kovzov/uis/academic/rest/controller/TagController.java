package by.kovzov.uis.academic.rest.controller;

import java.util.List;

import by.kovzov.uis.academic.dto.TagDto;
import by.kovzov.uis.academic.service.api.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('TAG_READ')")
    public Page<TagDto> search(@RequestParam(defaultValue = "") String name,
                                   @PageableDefault(sort = "name", direction = Direction.ASC) Pageable pageable) {
        return tagService.search(name, pageable);
    }

    @GetMapping("/parents")
    @PreAuthorize("hasAuthority('TAG_READ')")
    public Page<TagDto> getAllParents(@PageableDefault(sort = "name", direction = Direction.ASC) Pageable pageable) {
        return tagService.getAllParents(pageable);
    }

    @GetMapping("/{parentId}/children")
    @PreAuthorize("hasAuthority('TAG_READ')")
    public List<TagDto> getChildren(@PathVariable Long parentId) {
        return tagService.getAllChildren(parentId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('TAG_CREATE')")
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto create(@RequestBody @Valid TagDto tagDto) {
        return tagService.create(tagDto);
    }
}

package by.kovzov.uis.academic.service.api;

import java.util.List;

import by.kovzov.uis.academic.dto.TagDto;
import by.kovzov.uis.academic.dto.TagRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface TagService {

    Page<TagDto> search(String name, Pageable pageable);

    Page<TagDto> getAllParents(Pageable pageable);

    List<TagDto> getAllChildren(Long parentId, Sort sort);

    TagDto create(TagRequestDto tagDto);

    TagDto getDtoById(Long id);

    TagDto update(Long id, TagRequestDto tagDto);
}

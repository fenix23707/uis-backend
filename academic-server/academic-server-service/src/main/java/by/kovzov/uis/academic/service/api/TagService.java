package by.kovzov.uis.academic.service.api;

import java.util.List;

import by.kovzov.uis.academic.dto.TagDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {

    Page<TagDto> search(String name, Pageable pageable);

    Page<TagDto> getAllParents(Pageable pageable);

    List<TagDto> getAllChildren(Long parentId);

    TagDto create(TagDto tagDto);
}

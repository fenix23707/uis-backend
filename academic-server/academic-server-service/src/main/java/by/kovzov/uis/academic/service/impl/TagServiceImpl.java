package by.kovzov.uis.academic.service.impl;

import static by.kovzov.uis.academic.service.util.PageableUtils.pageableWithoutSort;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import by.kovzov.uis.academic.dto.TagDto;
import by.kovzov.uis.academic.repository.api.TagRepository;
import by.kovzov.uis.academic.repository.entity.Tag;
import by.kovzov.uis.academic.service.api.TagService;
import by.kovzov.uis.academic.service.mapper.TagMapper;
import by.kovzov.uis.common.exception.IdSpecifiedException;
import by.kovzov.uis.common.exception.NotFoundException;
import by.kovzov.uis.common.validator.unique.UniqueValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final UniqueValidationService uniqueValidationService;

    @Override
    public Page<TagDto> search(String name, Pageable pageable) {
        Page<Long> ids = tagRepository.findAllByNameLike(name, pageableWithoutSort(pageable))
            .map(Tag::getId);
        return getTagPage(ids, pageable);
    }

    @Override
    public Page<TagDto> getAllParents(Pageable pageable) {
        Page<Long> ids = tagRepository.findAllParents(pageableWithoutSort(pageable))
            .map(Tag::getId);
        return getTagPage(ids, pageable);
    }

    @Override
    public List<TagDto> getAllChildren(Long parentId, Sort sort) {
        getById(parentId);
        var ids = tagRepository.findAllChildrenByParentId(parentId, sort).stream()
            .map(Tag::getId)
            .collect(Collectors.toSet());
        return tagRepository.findAllWithChildrenByIds(ids, sort).stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
    }

    @Override
    public TagDto create(TagDto tagDto) {
        if (Objects.nonNull(tagDto.getId())) {
            throw new IdSpecifiedException();
        }

        Tag entity = tagMapper.toEntity(tagDto);
        uniqueValidationService.checkEntity(entity, tagRepository);

        return tagMapper.toDto(tagRepository.save(entity));
    }

    private Tag getById(Long id) {
        return tagRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Tag with id = %d not found.".formatted(id)));
    }

    private Page<TagDto> getTagPage(Page<Long> ids, Pageable pageable) {
        var content = tagRepository.findAllWithChildrenByIds(ids.toSet(), pageable.getSort());
        return PageableExecutionUtils.getPage(content, pageable, ids::getTotalElements)
            .map(this::mapToDto);
    }

    private TagDto mapToDto(Tag entity) {
        return tagMapper.toDto(entity).toBuilder()
            .hasChildren(!entity.getChildren().isEmpty())
            .build();
    }
}

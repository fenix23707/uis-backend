package by.kovzov.uis.academic.service.impl;

import static by.kovzov.uis.academic.service.util.PageableUtils.pageableWithoutSort;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import by.kovzov.uis.academic.dto.TagDto;
import by.kovzov.uis.academic.dto.TagRequestDto;
import by.kovzov.uis.academic.repository.api.TagRepository;
import by.kovzov.uis.academic.repository.entity.Tag;
import by.kovzov.uis.academic.service.api.TagService;
import by.kovzov.uis.academic.service.mapper.TagMapper;
import by.kovzov.uis.common.exception.NotFoundException;
import by.kovzov.uis.common.validator.unique.UniqueValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private static final String NOT_FOUND_MESSAGE = "Tag with id = %d not found.";
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
        verifyThatTagExists(parentId);
        var ids = tagRepository.findAllChildrenByParentId(parentId, sort).stream()
            .map(Tag::getId)
            .collect(Collectors.toSet());
        return tagRepository.findAllByIdsWithChildren(ids, sort).stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
    }

    @Override
    public TagDto getDtoById(Long id) {
        return tagRepository.findByIdWithChildren(id)
            .map(this::mapToDto)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE.formatted(id)));
    }

    @Override
    public TagDto create(TagRequestDto tagDto) {
        Tag entity = tagMapper.toEntity(tagDto);
        uniqueValidationService.checkEntity(entity, tagRepository);
        setParent(entity, tagDto.getParentId());
        return tagMapper.toDto(tagRepository.save(entity)).toBuilder()
            .hasChildren(false)
            .build();
    }

    @Override
    @Transactional
    public TagDto update(Long id, TagRequestDto tagDto) {
        TagDto existedTag = getDtoById(id);
        Tag entity = tagMapper.toEntity(tagDto);
        entity.setId(id);
        uniqueValidationService.checkEntity(entity, tagRepository);

        setParent(entity, tagDto.getParentId());
        return tagMapper.toDto(tagRepository.save(entity)).toBuilder()
            .hasChildren(existedTag.isHasChildren())
            .build();
    }

    private Tag getById(Long id) {
        return tagRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE.formatted(id)));
    }

    private void verifyThatTagExists(Long id) {
        if (tagRepository.existsById(id)) {
            throw new NotFoundException(NOT_FOUND_MESSAGE.formatted(id));
        }
    }

    private Page<TagDto> getTagPage(Page<Long> ids, Pageable pageable) {
        var content = tagRepository.findAllByIdsWithChildren(ids.toSet(), pageable.getSort());
        return PageableExecutionUtils.getPage(content, pageable, ids::getTotalElements)
            .map(this::mapToDto);
    }

    private TagDto mapToDto(Tag entity) {
        return tagMapper.toDto(entity).toBuilder()
            .hasChildren(!entity.getChildren().isEmpty())
            .build();
    }

    private void setParent(Tag entity, Long parentId) {
        entity.setParent(null);
        if (Objects.nonNull(parentId)) {
            Tag parent = getById(parentId);
            entity.setParent(parent);
        }
    }
}

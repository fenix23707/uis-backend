package by.kovzov.uis.academic.service.impl;

import java.util.List;
import java.util.Objects;

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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final UniqueValidationService uniqueValidationService;

    @Override
    public Page<TagDto> search(String name, Pageable pageable) {
        return tagRepository.findAllByNameLike(name, pageable)
            .map(tagMapper::toDto);
    }

    @Override
    public Page<TagDto> getAllParents(Pageable pageable) {
        return tagRepository.findAllParents(pageable)
            .map(tagMapper::toDto);
    }

    @Override
    public List<TagDto> getAllChildren(Long parentId) {
        getById(parentId);
        return tagMapper.toDto(tagRepository.findAllChildrenByParentId(parentId));
    }

    @Override
    public TagDto create(TagDto tagDto) {
        if (Objects.isNull(tagDto.getId())) {
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
}

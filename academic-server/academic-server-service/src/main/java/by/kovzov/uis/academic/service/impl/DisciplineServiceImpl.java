package by.kovzov.uis.academic.service.impl;

import static java.text.MessageFormat.format;

import static by.kovzov.uis.academic.service.util.PageableUtils.pageableWithoutSort;

import by.kovzov.uis.academic.dto.DisciplineDto;
import by.kovzov.uis.academic.repository.api.DisciplineRepository;
import by.kovzov.uis.academic.repository.entity.Discipline;
import by.kovzov.uis.academic.repository.specification.DisciplineSpecifications;
import by.kovzov.uis.academic.service.api.DisciplineService;
import by.kovzov.uis.academic.service.mapper.DisciplineMapper;
import by.kovzov.uis.common.exception.NotFoundException;
import by.kovzov.uis.common.validator.unique.UniqueValidationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DisciplineServiceImpl implements DisciplineService {

    private static final String NOT_FOUND_MESSAGE = "Discipline with id = %s not found.";

    private final DisciplineMapper disciplineMapper;
    private final DisciplineRepository disciplineRepository;

    private final UniqueValidationService uniqueValidationService;

    @Override
    @Transactional(readOnly = true)
    public DisciplineDto getById(Long id) {
        return disciplineRepository.findById(id)
            .map(disciplineMapper::toDto)
            .orElseThrow(() -> new NotFoundException(format(NOT_FOUND_MESSAGE, id)));
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public Page<DisciplineDto> search(String query, Pageable pageable) {
        var specification = DisciplineSpecifications.mameLike(query)
            .or(DisciplineSpecifications.shortNameLike(query));
        var ids = disciplineRepository.findAll(specification, pageableWithoutSort(pageable))
            .map(Discipline::getId);
        var content = disciplineRepository.findAllWithTagsByIds(ids.toSet(), pageable.getSort());
        return PageableExecutionUtils.getPage(content, pageable, ids::getTotalElements)
            .map(disciplineMapper::toDto);
    }

    @Override
    public DisciplineDto create(DisciplineDto disciplineDto) {
        Discipline entity = disciplineMapper.toEntity(disciplineDto);
        entity.setId(null);
        uniqueValidationService.checkEntity(entity, disciplineRepository);
        return disciplineMapper.toDto(disciplineRepository.save(entity));
    }

    @Override
    public DisciplineDto update(Long id, DisciplineDto disciplineDto) {
        getById(id); // will throw an exception if id is not exist
        Discipline entity = disciplineMapper.toEntity(disciplineDto);
        entity.setId(id);
        uniqueValidationService.checkEntity(entity, disciplineRepository);
        return disciplineMapper.toDto(disciplineRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        verifyThatExistsById(id);
        disciplineRepository.deleteById(id);
    }

    @Override
    public void verifyThatExistsById(Long id) {
        if (!disciplineRepository.existsById(id)) {
            throw new NotFoundException(NOT_FOUND_MESSAGE.formatted(id));
        }
    }
}

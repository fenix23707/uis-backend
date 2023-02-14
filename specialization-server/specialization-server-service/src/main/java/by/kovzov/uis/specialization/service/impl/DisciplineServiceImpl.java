package by.kovzov.uis.specialization.service.impl;

import static java.text.MessageFormat.format;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.kovzov.uis.common.exception.NotFoundException;
import by.kovzov.uis.specialization.dto.DisciplineDto;
import by.kovzov.uis.specialization.repository.api.DisciplineRepository;
import by.kovzov.uis.specialization.repository.entity.Discipline;
import by.kovzov.uis.specialization.repository.specification.DisciplineSpecifications;
import by.kovzov.uis.specialization.service.api.DisciplineService;
import by.kovzov.uis.specialization.service.mapper.DisciplineMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DisciplineServiceImpl implements DisciplineService {

    private static final String NOT_FOUND_MESSAGE = "Discipline with id = {0} not found.";

    private final DisciplineMapper disciplineMapper;
    private final DisciplineRepository disciplineRepository;

    @Override
    @Transactional(readOnly = true)
    public DisciplineDto getById(Long id) {
        return disciplineRepository.findById(id)
            .map(disciplineMapper::toDto)
            .orElseThrow(() -> new NotFoundException(format(NOT_FOUND_MESSAGE, id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DisciplineDto> search(String query, Pageable pageable) {
        Specification<Discipline> specification = DisciplineSpecifications.mameLike(query)
            .or(DisciplineSpecifications.shortNameLike(query));
        return disciplineRepository.findAll(specification, pageable)
            .map(disciplineMapper::toDto);
    }
}

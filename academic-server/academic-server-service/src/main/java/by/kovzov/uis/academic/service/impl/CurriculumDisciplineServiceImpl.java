package by.kovzov.uis.academic.service.impl;

import java.util.List;

import by.kovzov.uis.academic.dto.CurriculumDisciplineDto;
import by.kovzov.uis.academic.repository.api.CurriculumDisciplineRepository;
import by.kovzov.uis.academic.repository.entity.CurriculumDiscipline;
import by.kovzov.uis.academic.repository.entity.CurriculumDiscipline.CurriculumDisciplineId;
import by.kovzov.uis.academic.service.api.CurriculumDisciplineService;
import by.kovzov.uis.academic.service.api.CurriculumService;
import by.kovzov.uis.academic.service.api.DisciplineService;
import by.kovzov.uis.academic.service.mapper.CurriculumDisciplineMapper;
import by.kovzov.uis.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CurriculumDisciplineServiceImpl implements CurriculumDisciplineService {

    private static final String NOT_FOUND_MESSAGE = "Curriculum discipline with id = %s not found.";

    private final CurriculumDisciplineRepository curriculumDisciplineRepository;
    private final CurriculumDisciplineMapper curriculumDisciplineMapper;
    private final CurriculumService curriculumService;
    private final DisciplineService disciplineService;

    @Override
    public List<CurriculumDisciplineDto> getAllByCurriculumId(Long curriculumId, Sort sort) {
        curriculumService.verifyThatExistsById(curriculumId);
        var entities = curriculumDisciplineRepository.findAllByCurriculumId(curriculumId, sort);
        return curriculumDisciplineMapper.toDtos(entities);
    }

    @Override
    @Transactional
    public CurriculumDisciplineDto create(CurriculumDisciplineId id, CurriculumDisciplineDto dto) {
        curriculumService.verifyThatExistsById(id.getCurriculumId());
        disciplineService.verifyThatExistsById(id.getDisciplineId());
        var entity = curriculumDisciplineMapper.toEntity(dto);
        entity.setId(id);
        var savedEntity = curriculumDisciplineRepository.save(entity);
        return curriculumDisciplineMapper.toDto(savedEntity);
    }

    @Override
    @Transactional
    public CurriculumDisciplineDto update(CurriculumDisciplineId id, CurriculumDisciplineDto dto) {
        verifyThatExists(id);
        var entity = curriculumDisciplineMapper.toEntity(dto);
        entity.setId(id);
        var updatedEntity = curriculumDisciplineRepository.save(entity);
        return curriculumDisciplineMapper.toDto(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(CurriculumDisciplineId id) {
        var entity = curriculumDisciplineRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE.formatted(id)));
        curriculumDisciplineRepository.delete(entity);
    }

    private void verifyThatExists(CurriculumDisciplineId id) {
        if (!curriculumDisciplineRepository.existsById(id)) {
            throw new NotFoundException(NOT_FOUND_MESSAGE.formatted(id));
        }
    }
}

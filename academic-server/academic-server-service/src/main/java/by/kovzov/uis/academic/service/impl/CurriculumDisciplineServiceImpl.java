package by.kovzov.uis.academic.service.impl;

import java.util.List;

import by.kovzov.uis.academic.dto.CurriculumDisciplineDto;
import by.kovzov.uis.academic.repository.api.CurriculumDisciplineRepository;
import by.kovzov.uis.academic.service.api.CurriculumDisciplineService;
import by.kovzov.uis.academic.service.api.CurriculumService;
import by.kovzov.uis.academic.service.api.DisciplineService;
import by.kovzov.uis.academic.service.mapper.CurriculumDisciplineMapper;
import by.kovzov.uis.academic.service.mapper.CurriculumMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CurriculumDisciplineServiceImpl implements CurriculumDisciplineService {

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
    public CurriculumDisciplineDto create(CurriculumDisciplineDto dto) {
        curriculumService.verifyThatExistsById(dto.getCurriculumId());
        disciplineService.verifyThatExistsById(dto.getDisciplineId());
        var entity = curriculumDisciplineMapper.toEntity(dto);
        var savedEntity = curriculumDisciplineRepository.save(entity);
        return curriculumDisciplineMapper.toDto(savedEntity);
    }

}

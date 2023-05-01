package by.kovzov.uis.academic.service.impl;

import java.util.List;

import by.kovzov.uis.academic.dto.CurriculumDisciplineDto;
import by.kovzov.uis.academic.repository.api.CurriculumDisciplineRepository;
import by.kovzov.uis.academic.service.api.CurriculumDisciplineService;
import by.kovzov.uis.academic.service.api.CurriculumService;
import by.kovzov.uis.academic.service.mapper.CurriculumDisciplineMapper;
import by.kovzov.uis.academic.service.mapper.CurriculumMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurriculumDisciplineServiceImpl implements CurriculumDisciplineService {

    private final CurriculumDisciplineRepository curriculumDisciplineRepository;
    private final CurriculumDisciplineMapper curriculumDisciplineMapper;
    private final CurriculumService curriculumService;

    @Override
    public List<CurriculumDisciplineDto> getAllByCurriculumId(Long curriculumId) {
        curriculumService.verifyThatExistsById(curriculumId);
        var entities = curriculumDisciplineRepository.findAllByCurriculumId(curriculumId);
        return curriculumDisciplineMapper.toDtos(entities);
    }
}

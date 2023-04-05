package by.kovzov.uis.academic.service.impl;

import by.kovzov.uis.academic.dto.CurriculumDto;
import by.kovzov.uis.academic.repository.api.CurriculumRepository;
import by.kovzov.uis.academic.repository.entity.Curriculum;
import by.kovzov.uis.academic.service.api.CurriculumService;
import by.kovzov.uis.academic.service.mapper.CurriculumMapper;
import by.kovzov.uis.common.validator.unique.UniqueValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurriculumServiceImpl implements CurriculumService {

    private final CurriculumRepository curriculumRepository;
    private final CurriculumMapper curriculumMapper;
    private final UniqueValidationService uniqueValidationService;

    @Override
    public Page<CurriculumDto> getAll(Pageable pageable) {
        return curriculumRepository.findAll(pageable)
            .map(curriculumMapper::toDto);
    }

    @Override
    public CurriculumDto create(CurriculumDto curriculumDto) {
        Curriculum entity = curriculumMapper.toEntity(curriculumDto);
//        uniqueValidationService.checkEntity(entity, curriculumRepository);
        return curriculumMapper.toDto(curriculumRepository.save(entity));
    }
}

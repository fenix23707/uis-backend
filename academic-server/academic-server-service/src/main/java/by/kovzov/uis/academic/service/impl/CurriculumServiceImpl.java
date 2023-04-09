package by.kovzov.uis.academic.service.impl;

import by.kovzov.uis.academic.dto.CurriculumDto;
import by.kovzov.uis.academic.dto.SearchDto;
import by.kovzov.uis.academic.repository.api.CurriculumRepository;
import by.kovzov.uis.academic.repository.entity.Curriculum;
import by.kovzov.uis.academic.service.api.CurriculumService;
import by.kovzov.uis.academic.service.mapper.CurriculumMapper;
import by.kovzov.uis.common.exception.NotFoundException;
import by.kovzov.uis.common.validator.unique.UniqueValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurriculumServiceImpl implements CurriculumService {

    private static final String NOT_FOUND_MESSAGE = "Curriculum with id = %d not found";

    private final CurriculumRepository curriculumRepository;
    private final CurriculumMapper curriculumMapper;
    private final UniqueValidationService uniqueValidationService;

    @Override
    public Page<CurriculumDto> getAll(Pageable pageable) {
        return curriculumRepository.findAll(pageable)
            .map(curriculumMapper::toDto);
    }

    @Override
    public CurriculumDto getById(Long id) {
        var entity = curriculumRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE.formatted(id)));
        return curriculumMapper.toDto(entity);
    }

    @Override
    public Page<CurriculumDto> search(SearchDto searchDto, Pageable pageable) {
        throw new UnsupportedOperationException("the search has not been implemented yet");
    }

    @Override
    public CurriculumDto create(CurriculumDto curriculumDto) {
        Curriculum entity = curriculumMapper.toEntity(curriculumDto);
//        uniqueValidationService.checkEntity(entity, curriculumRepository);
        return curriculumMapper.toDto(curriculumRepository.save(entity));
    }

    @Override
    public CurriculumDto update(Long id, CurriculumDto curriculumDto) {
        verifyThatCurriculumExists(id);
        Curriculum entity = curriculumMapper.toEntity(curriculumDto);
        entity.setId(id);
        return curriculumMapper.toDto(curriculumRepository.save(entity));
    }

    private void verifyThatCurriculumExists(Long id) {
        if (!curriculumRepository.existsById(id)) {
            throw new NotFoundException(NOT_FOUND_MESSAGE.formatted(id));
        }
    }


}

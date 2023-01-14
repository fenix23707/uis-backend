package by.kovzov.uis.api.contoller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.kovzov.uis.domain.dto.pageable.SpecializationPageableDto;
import by.kovzov.uis.service.api.SpecializationService;
import lombok.AllArgsConstructor;
// Help https://www.baeldung.com/spring-data-web-support
@RestController
@RequestMapping("/api/specializations")
@AllArgsConstructor
public class SpecializationController {

    private final SpecializationService specializationService;

    @GetMapping("/parents")
    public SpecializationPageableDto getAllParents(@PageableDefault(sort = "name", direction = Direction.ASC) Pageable pageable) {
        return specializationService.getAllParents(pageable);
    }
}

package by.kovzov.uis.specialization.domain.dto.pageable;

import org.springframework.data.domain.Page;

import java.util.List;

import lombok.Data;

@Data
abstract public class PageableDto<T> {

    private List<T> content;
    private int currentPage;
    private int totalPages;
    private long totalItems;

    public PageableDto(Page<T> specializations) {
        this.content = specializations.getContent();
        this.currentPage = specializations.getNumber();
        this.totalPages = specializations.getTotalPages();
        this.totalItems = specializations.getTotalElements();
    }
}

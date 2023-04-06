package by.kovzov.uis.academic.service.mapper;

import java.util.List;

import by.kovzov.uis.academic.dto.TagDto;
import by.kovzov.uis.academic.repository.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TagMapper {

    @Mapping(target = "parentId", source = "parent.id")
    TagDto toDto(Tag entity);

    @Mapping(target = "parentId", source = "parent.id")
    List<TagDto> toDto(List<Tag> entity);

    @Mapping(target = "parent.id", source = "parent.id")
    Tag toEntity(TagDto dto);
}

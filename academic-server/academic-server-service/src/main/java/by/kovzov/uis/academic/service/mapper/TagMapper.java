package by.kovzov.uis.academic.service.mapper;

import java.util.List;

import by.kovzov.uis.academic.dto.TagDto;
import by.kovzov.uis.academic.repository.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TagMapper {

    @Mappings({
        @Mapping(source = "parent.id", target = "parent.id")
    })
    TagDto toDto(Tag entity);

    @Mappings({
        @Mapping(source = "parent.id", target = "parent.id")
    })
    List<TagDto> toDto(List<Tag> entity);

    @Mappings({
        @Mapping(source = "parent.id", target = "parent.id")
    })
    Tag toEntity(TagDto dto);
}

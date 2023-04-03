package by.kovzov.uis.academic.service.mapper;

import java.util.List;
import java.util.Objects;

import by.kovzov.uis.academic.dto.TagDto;
import by.kovzov.uis.academic.repository.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TagMapper {

    @Mapping(target = "parent", qualifiedByName = "mapParent")
    TagDto toDto(Tag entity);

    @Mapping(target = "parent", qualifiedByName = "mapParent")
    List<TagDto> toDto(List<Tag> entity);

    @Mapping(target = "parent", qualifiedByName = "mapParent")
    Tag toEntity(TagDto dto);

    @Named("mapParent")
    default TagDto mapParent(Tag tag) {
        if (Objects.isNull(tag)) {
            return null;
        }
        return TagDto.builder()
            .id(tag.getId())
            .build();
    }

    @Named("mapParent")
    default Tag mapParent(TagDto dto) {
        Tag entity = new Tag();
        entity.setId(dto.getId());
        return entity;
    }
}

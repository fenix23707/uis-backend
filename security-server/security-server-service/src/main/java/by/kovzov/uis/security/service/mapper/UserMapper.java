package by.kovzov.uis.security.service.mapper;

import by.kovzov.uis.security.dto.UserCreateDto;
import by.kovzov.uis.security.dto.UserDto;
import by.kovzov.uis.security.repository.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserCreateDto userDto);
}

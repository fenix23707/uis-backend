package by.kovzov.uis.security.service.mapper;

import java.util.Collection;
import java.util.Set;

import by.kovzov.uis.security.dto.MethodDto;
import by.kovzov.uis.security.repository.entity.Method;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MethodMapper {

    Set<Method> toEntities(Collection<MethodDto> methods);

}

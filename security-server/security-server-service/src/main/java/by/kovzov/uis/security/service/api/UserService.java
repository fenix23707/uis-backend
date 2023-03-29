package by.kovzov.uis.security.service.api;


import by.kovzov.uis.security.dto.UserDto;
import by.kovzov.uis.security.repository.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserDto getById(Long id);

    User getByUsername(String username);


    Page<UserDto> search(String username, Pageable pageable);
}

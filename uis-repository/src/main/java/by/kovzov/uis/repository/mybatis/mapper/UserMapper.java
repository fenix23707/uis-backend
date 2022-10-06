package by.kovzov.uis.repository.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

import by.kovzov.uis.domain.model.user.User;
import by.kovzov.uis.domain.model.user.UserRole;

@Mapper
public interface UserMapper {

    User findById(@Param("id") Long id);

    User findByUsername(@Param("username") String username);

    Long insert(User user);

    void insertUserRoles(@Param("userId") Long userId ,@Param("roles") Set<UserRole> roles);
}

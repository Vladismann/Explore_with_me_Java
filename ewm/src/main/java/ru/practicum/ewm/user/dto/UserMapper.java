package ru.practicum.ewm.user.dto;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import ru.practicum.ewm.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserMapper {

    public User NewUserRequestToUser(NewUserRequest newUserRequest) {
        return User.builder()
                .email(newUserRequest.getEmail())
                .name(newUserRequest.getName())
                .build();
    }

    public UserDto userToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public List<UserDto> userToUserDto(List<User> users) {
        return users.stream().map(UserMapper::userToUserDto).collect(Collectors.toList());
    }

    public List<UserDto> userToUserDto(Page<User> users) {
        return users.stream().map(UserMapper::userToUserDto).collect(Collectors.toList());
    }
}

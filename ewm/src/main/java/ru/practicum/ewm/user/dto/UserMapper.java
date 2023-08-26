package ru.practicum.ewm.user.dto;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import ru.practicum.ewm.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserMapper {

    public User createUserDtoToUser(CreateUserDto createUserDto) {
        return User.builder()
                .email(createUserDto.getEmail())
                .name(createUserDto.getName())
                .build();
    }

    public GetUserDto userToGetUserDto(User user) {
        return GetUserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public List<GetUserDto> userToGetUserDto(List<User> users) {
        return users.stream().map(UserMapper::userToGetUserDto).collect(Collectors.toList());
    }

    public List<GetUserDto> userToGetUserDto(Page<User> users) {
        return users.stream().map(UserMapper::userToGetUserDto).collect(Collectors.toList());
    }
}

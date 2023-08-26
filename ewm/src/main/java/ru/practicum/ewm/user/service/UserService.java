package ru.practicum.ewm.user.service;

import ru.practicum.ewm.user.dto.CreateUserDto;
import ru.practicum.ewm.user.dto.GetUserDto;

import java.util.List;

public interface UserService {

    GetUserDto createUser(CreateUserDto dto);

    void deleteUser(long id);

    List<GetUserDto> getUsers(List<Long> ids, long from, long size);
}

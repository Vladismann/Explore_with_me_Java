package ru.practicum.ewm.user.service;

import ru.practicum.ewm.user.dto.NewUserRequest;
import ru.practicum.ewm.user.dto.SubscriptionsDto;
import ru.practicum.ewm.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(NewUserRequest dto);

    void deleteUser(long id);

    List<UserDto> getUsers(List<Long> ids, int from, int size);

    List<SubscriptionsDto> subscribe(long subscriberId, long userId);

    List<SubscriptionsDto> unsubscribe(long subscriberId, long userId);
}

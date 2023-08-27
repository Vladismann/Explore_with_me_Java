package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.common.CommonMethods;
import ru.practicum.ewm.common.CustomPageRequest;
import ru.practicum.ewm.user.dto.NewUserRequest;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.dto.UserMapper;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repo.UserRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public UserDto createUser(NewUserRequest dto) {
        User user = userRepo.save(UserMapper.NewUserRequestToUser(dto));
        log.info("Пользователь зарегистрирован: {}", user);
        return UserMapper.userToUserDto(user);
    }

    @Override
    public void deleteUser(long id) {
        CommonMethods.checkObjectIsExists(id, userRepo);
        userRepo.deleteById(id);
        log.info("Пользователь удален: {}", id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        Pageable pageable = new CustomPageRequest(from, size);
        List<UserDto> users;
        if (ids == null || ids.isEmpty()) {
            users = UserMapper.userToUserDto(userRepo.findAll(pageable));
        } else {
            users = UserMapper.userToUserDto(userRepo.findByIdIn(ids, pageable));
        }
        log.info("Запрошен список пользователей в размере: {}", users.size());
        return users;
    }
}
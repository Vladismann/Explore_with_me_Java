package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.common.CustomPageRequest;
import ru.practicum.ewm.exceptions.NotFoundException;
import ru.practicum.ewm.user.dto.CreateUserDto;
import ru.practicum.ewm.user.dto.GetUserDto;
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
    public GetUserDto createUser(CreateUserDto dto) {
        User user = userRepo.save(UserMapper.createUserDtoToUser(dto));
        log.info("Пользователь зарегистрирован: {}", user);
        return UserMapper.userToGetUserDto(user);
    }

    @Override
    public void deleteUser(long id) {
        if (!userRepo.existsById(id)) {
            throw new NotFoundException(String.format("User with id=%s was not found", id));
        }
        userRepo.deleteById(id);
        log.info("Пользователь удален: {}", id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<GetUserDto> getUsers(List<Long> ids, int from, int size) {
        Pageable pageable = new CustomPageRequest(from, size);
        List<GetUserDto> users;
        if (ids == null || ids.isEmpty()) {
            users = UserMapper.userToGetUserDto(userRepo.findAll(pageable));
        } else {
            users = UserMapper.userToGetUserDto(userRepo.findByIdIn(ids, pageable));
        }
        log.info("Запрошен список пользователей в размере: {}", users.size());
        return users;
    }
}
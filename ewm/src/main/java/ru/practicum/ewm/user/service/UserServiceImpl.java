package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public List<GetUserDto> getUsers(List<Long> ids, long from, long size) {
        return null;
    }
}

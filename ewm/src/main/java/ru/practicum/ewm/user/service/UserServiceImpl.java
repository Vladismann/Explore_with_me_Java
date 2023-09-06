package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.common.CommonMethods;
import ru.practicum.ewm.common.CustomPageRequest;
import ru.practicum.ewm.exceptions.NotFoundException;
import ru.practicum.ewm.user.dto.NewUserRequest;
import ru.practicum.ewm.user.dto.SubscriptionsDto;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.dto.UserMapper;
import ru.practicum.ewm.user.model.Subscription;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repo.SubscriptionsRepo;
import ru.practicum.ewm.user.repo.UserRepo;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final SubscriptionsRepo subscriptionsRepo;

    @Override
    public UserDto createUser(NewUserRequest dto) {
        User user = userRepo.save(UserMapper.newUserRequestToUser(dto));
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

    @Override
    public List<SubscriptionsDto> subscribe(long subscriberId, long userId) {
        User subscriber = userRepo.getReferenceById(subscriberId);
        User user = userRepo.getReferenceById(userId);
        if (!user.isSubscribers()) {
            throw new IllegalArgumentException("Subscription not available for user with id=" + userId);
        }
        Subscription existsSubscription = subscriptionsRepo.findBySubscriberIdAndUserId(subscriberId, userId);
        if (existsSubscription != null) {
            throw new IllegalArgumentException("Already subscribed.");
        }
        Subscription subscription = Subscription.builder().subscriber(subscriber).user(user).created(LocalDateTime.now()).build();
        subscriptionsRepo.save(subscription);
        log.info("Пользователь с id={} подписался на пользователя с id={}", subscriberId, userId);
        return UserMapper.subscriptionsToSubscriptionDto(subscriptionsRepo.findBySubscriberId(subscriberId));
    }

    @Override
    public List<SubscriptionsDto> unsubscribe(long subscriberId, long userId) {
        CommonMethods.checkObjectIsExists(subscriberId, userRepo);
        CommonMethods.checkObjectIsExists(userId, userRepo);
        Subscription existsSubscription = subscriptionsRepo.findBySubscriberIdAndUserId(subscriberId, userId);
        if (existsSubscription == null) {
            throw new NotFoundException("Subscription not found.");
        }
        subscriptionsRepo.deleteById(existsSubscription.getId());
        log.info("Пользователь с id={} отменил подписку на пользователя с id={}", subscriberId, userId);
        return UserMapper.subscriptionsToSubscriptionDto(subscriptionsRepo.findBySubscriberId(subscriberId));
    }
}
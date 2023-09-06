package ru.practicum.ewm.user.dto;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import ru.practicum.ewm.user.model.Subscription;
import ru.practicum.ewm.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserMapper {

    public User newUserRequestToUser(NewUserRequest newUserRequest) {
        User user = User.builder()
                .email(newUserRequest.getEmail())
                .name(newUserRequest.getName())
                .build();
        //Проверяем, что пользователь при регистрации, возможно, сразу отменил подписки на себя. Если не указано, то по умолчанию = true
        if (newUserRequest.getSubscribers() != null) {
            user.setSubscribers(newUserRequest.getSubscribers());
        } else {
            user.setSubscribers(true);
        }
        return user;
    }

    public UserDto userToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .subscribers(user.isSubscribers())
                .build();
    }

    public List<UserDto> userToUserDto(List<User> users) {
        return users.stream().map(UserMapper::userToUserDto).collect(Collectors.toList());
    }

    public List<UserDto> userToUserDto(Page<User> users) {
        return users.stream().map(UserMapper::userToUserDto).collect(Collectors.toList());
    }

    public List<SubscriptionsDto> subscriptionsToSubscriptionDto(List<Subscription> subscriptions) {
        return subscriptions.stream()
                .map(subscription -> SubscriptionsDto.builder()
                        .id(subscription.getId())
                        .userId(subscription.getUser().getId())
                        .userName(subscription.getUser().getName()).build())
                .collect(Collectors.toList());
    }
}

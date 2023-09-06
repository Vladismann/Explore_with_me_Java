package ru.practicum.ewm.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.dto.SubscriptionsDto;
import ru.practicum.ewm.user.service.UserService;

import javax.validation.constraints.Positive;
import java.util.List;

import static ru.practicum.dto.Common.Messages.RECEIVED_DELETE;
import static ru.practicum.dto.Common.Messages.RECEIVED_POST;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
@Validated
public class PrivateUserController {

    private final UserService userService;

    @PostMapping("/subscriptions")
    @ResponseStatus(HttpStatus.CREATED)
    public List<SubscriptionsDto> subscribeToUser(@RequestParam @Positive long subscriberId,
                                                  @RequestParam @Positive long userId) {
        log.info(RECEIVED_POST, "/users/subscriptions", subscriberId);
        return userService.subscribe(subscriberId, userId);
    }

    @DeleteMapping("/subscriptions")
    public List<SubscriptionsDto> deleteSubscription(@RequestParam @Positive long subscriberId,
                                                     @RequestParam @Positive long userId) {
        log.info(RECEIVED_DELETE, "/users/subscriptions", subscriberId);
        return userService.unsubscribe(subscriberId, userId);
    }
}

package ru.practicum.ewm.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.service.UserService;

import javax.validation.constraints.Positive;

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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void subscribeToUser(@RequestParam @Positive long subscriberId,
                                @RequestParam @Positive long userId) {
        log.info(RECEIVED_POST, "/users/subscriptions", subscriberId);
        userService.subscribe(subscriberId, userId);
    }

    @DeleteMapping("/subscriptions")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubscription(@RequestParam @Positive long subscriberId,
                                   @RequestParam @Positive long userId) {
        log.info(RECEIVED_DELETE, "/users/subscriptions", subscriberId);
        userService.unsubscribe(subscriberId, userId);
    }
}

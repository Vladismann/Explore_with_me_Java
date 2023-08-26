package ru.practicum.ewm.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.dto.CreateUserDto;
import ru.practicum.ewm.user.dto.GetUserDto;
import ru.practicum.ewm.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

import static ru.practicum.dto.Common.CommonForPaths.BY_ID_PATH;
import static ru.practicum.dto.Common.Messages.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public GetUserDto createUser(@Valid @RequestBody CreateUserDto user) {
        log.info(RECEIVED_POST, "/admin/users");
        return userService.createUser(user);
    }

    @DeleteMapping(BY_ID_PATH)
    public void deleteUser(@PathVariable long id) {
        log.info(RECEIVED_DELETE, "/admin/users", id);
        userService.deleteUser(id);
    }

    @GetMapping()
    public List<GetUserDto> getAllUsers(@RequestParam(required = false) List<Long> ids,
                                        @RequestParam(defaultValue = "0") int from,
                                        @RequestParam(defaultValue = "10") int size) {
        log.info(RECEIVED_GET, "/admin/users");
        return userService.getUsers(ids, from, size);
    }

}
package com.yofujitsu.rksp7.controller;

import com.yofujitsu.rksp7.dto.UserCreateDto;
import com.yofujitsu.rksp7.dto.UserDto;
import com.yofujitsu.rksp7.dto.UserUpdateDto;
import com.yofujitsu.rksp7.entity.User;
import com.yofujitsu.rksp7.mapper.UserMapper;
import com.yofujitsu.rksp7.repos.UserRepository;
import com.yofujitsu.rksp7.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping
    public Flux<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserDto> getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @PostMapping("/create")
    public ResponseEntity<Mono<UserDto>> createUser(@RequestBody UserCreateDto userDto) {
        Mono<UserDto> user = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteUser(@PathVariable UUID id) {
        return userService.deleteUser(id);
    }

    @PostMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserDto> updateUser(@PathVariable String username, @RequestBody UserUpdateDto userDto) {
        return userService.updateUser(username, userDto);
    }
}

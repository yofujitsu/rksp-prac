package com.yofujitsu.rksp7.service.user;

import com.yofujitsu.rksp7.dto.UserCreateDto;
import com.yofujitsu.rksp7.dto.UserDto;
import com.yofujitsu.rksp7.dto.UserUpdateDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService {

    Flux<UserDto> getUsers();

    Mono<UserDto> createUser(UserCreateDto userCreateDto);

    Mono<UserDto> getUser(String username);

    Mono<Void> deleteUser(UUID id);

    Mono<UserDto> updateUser(String username, UserUpdateDto userUpdateDto);


}

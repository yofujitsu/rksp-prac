package com.yofujitsu.rksp7.service.user;

import com.yofujitsu.rksp7.dto.UserCreateDto;
import com.yofujitsu.rksp7.dto.UserDto;
import com.yofujitsu.rksp7.dto.UserUpdateDto;
import com.yofujitsu.rksp7.entity.User;
import com.yofujitsu.rksp7.exception.EntityNotFoundException;
import com.yofujitsu.rksp7.mapper.UserMapper;
import com.yofujitsu.rksp7.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Flux<UserDto> getUsers() {
        return userRepository.findAll()
                .map(userMapper::toUserDto)
                .onErrorResume(e -> Flux.error(new EntityNotFoundException()))
                .onBackpressureBuffer();
    }

    @Override
    public Mono<UserDto> createUser(UserCreateDto userCreateDto) {
        return Mono.just(userMapper.toUser(userCreateDto))
                .flatMap(userRepository::save)
                .map(userMapper::toUserDto);
    }

    @Override
    public Mono<UserDto> getUser(String username) {
        return findUserByUsername(username)
                .map(userMapper::toUserDto);
    }

    @Override
    public Mono<Void> deleteUser(UUID id) {
        return userRepository.deleteById(id);
    }

    @Override
    public Mono<UserDto> updateUser(String username, UserUpdateDto userUpdateDto) {
        return findUserByUsername(username)
                .flatMap(user -> {
                    userMapper.updateUser(user, userUpdateDto);
                    return userRepository.save(user);
                })
                .map(userMapper::toUserDto);
    }

    private Mono<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username).switchIfEmpty(Mono.error(new EntityNotFoundException()));
    }
}

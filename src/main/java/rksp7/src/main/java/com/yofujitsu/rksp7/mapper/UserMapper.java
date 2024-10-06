package com.yofujitsu.rksp7.mapper;

import com.yofujitsu.rksp7.dto.UserCreateDto;
import com.yofujitsu.rksp7.dto.UserDto;
import com.yofujitsu.rksp7.dto.UserUpdateDto;
import com.yofujitsu.rksp7.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDto toUserDto(User user);

    User toUser(UserCreateDto userCreateDto);

    void updateUser(@MappingTarget User user, UserUpdateDto userUpdateDto);
}

package com.yofujitsu.rksp7.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class UserUpdateDto {
    private String username;
}
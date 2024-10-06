package com.yofujitsu.rksp7.entity;

import lombok.*;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    private UUID id;

    private String username;

    private LocalDateTime createdAt;

}

package com.yofujitsu.rsocketclient.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Agent {
    private UUID id;
    private String name;
    private Role role;
    private String description;
}

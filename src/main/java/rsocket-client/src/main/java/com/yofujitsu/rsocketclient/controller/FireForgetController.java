package com.yofujitsu.rsocketclient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/agents")
@RequiredArgsConstructor
public class FireForgetController {

    private final RSocketRequester rSocketRequester;

    @PutMapping("/{name}")
    public void updateAgent(@PathVariable String name) {
        rSocketRequester.route("updateAgent")
                .data(name)
                .send();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAgent(@PathVariable UUID id) {
        rSocketRequester.route("deleteAgent")
                .data(id)
                .send();
    }
}

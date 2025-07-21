package com.bell.springstub.controller;

import com.bell.springstub.DataBaseWorker;
import com.bell.springstub.model.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class StubController {

    private final DataBaseWorker dbWorker = new DataBaseWorker();

    @GetMapping("/get")
    public ResponseEntity<User> get(@RequestParam String login) {
        makeDelay();
        User user = dbWorker.getUserByLogin(login);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/post")
    public ResponseEntity<User> post(@Valid @RequestBody User user) {
        makeDelay();
        user.setDate(new Date());
        dbWorker.insertUser(user);
        return ResponseEntity.ok(user);
    }

    private void makeDelay() {
        try {
            Thread.sleep(1000 + (long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

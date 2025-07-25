package com.bell.springstub.controller;

import com.bell.springstub.DataBaseWorker;
import com.bell.springstub.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StubController {

    private final DataBaseWorker dbWorker;

    private static final List<User> ARRAY_USERS = new ArrayList<>();


    @Autowired
    public StubController(DataBaseWorker dbWorker) {
        this.dbWorker = dbWorker;
    }

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

    @GetMapping("/leak")
    public ResponseEntity<String> leak(@RequestParam String login) {
        makeDelay();
        User user = dbWorker.getUserByLogin(login);
        for (int i = 0; i < 100; i++) {
            ARRAY_USERS.add(user);
        }
        return ResponseEntity.ok("утечка памяти. Пользователь: " + user.getLogin());
    }

    private void makeDelay() {
        try {
            Thread.sleep(1000 + (long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

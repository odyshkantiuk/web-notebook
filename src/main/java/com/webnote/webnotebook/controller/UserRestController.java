package com.webnote.webnotebook.controller;

import com.webnote.webnotebook.dao.entity.User;
import com.webnote.webnotebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/name")
    public ResponseEntity<User> get(@RequestParam String name) {
        User user = userService.get(name);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/password")
    public ResponseEntity<User> getByPassword(@RequestParam String password) {
        User user = userService.getByPassword(password);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> add(@RequestBody User user) {
        for (User name : userService.getAll()) {
            if (user.getName().equals(name.getName())) {
                return ResponseEntity.badRequest().build();
            }
        }
        userService.add(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> update(@PathVariable Integer userId, @RequestBody User user) {
        for (User name : userService.getAll()) {
            if (user.getName().equals(name.getName())) {
                return ResponseEntity.badRequest().build();
            }
        }
        if (userService.get(userId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        user.setId(userId);
        userService.update(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Integer> delete(@PathVariable Integer userId) {
        if (userService.get(userId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userService.delete(userId);
        return ResponseEntity.ok(userId);
    }
}

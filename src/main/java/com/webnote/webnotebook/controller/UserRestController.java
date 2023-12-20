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

    @PostMapping
    public ResponseEntity<String> add(@RequestBody User user) {
        for (User name : userService.getAll()) {
            if (user.getName().equals(name.getName())) {
                return ResponseEntity.badRequest().build();
            }
        }
        userService.add(user);
        return ResponseEntity.ok("User added successfully");
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> update(@PathVariable Integer userId, @RequestBody User user) {
        for (User name : userService.getAll()) {
            if (user.getName().equals(name.getName())) {
                return ResponseEntity.badRequest().build();
            }
        }
        if (userService.get(userId) == null) {
            return ResponseEntity.notFound().build();
        }
        user.setId(userId);
        userService.update(user);
        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> delete(@PathVariable Integer userId) {
        if (userService.get(userId) == null) {
            return ResponseEntity.notFound().build();
        }
        userService.delete(userId);
        return ResponseEntity.ok("User deleted successfully");
    }
}

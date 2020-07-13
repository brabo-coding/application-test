package com.poc.tekton.app.controller;

import com.poc.tekton.app.model.User;
import com.poc.tekton.app.repository.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private Users users;

    @Autowired
    public UserController(Users users) {
        this.users = users;
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        this.users.save(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable("id") final String id) {
        return ResponseEntity.ok().body(this.users.get(id).get());
    }

}

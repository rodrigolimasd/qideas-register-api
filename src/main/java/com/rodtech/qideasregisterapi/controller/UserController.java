package com.rodtech.qideasregisterapi.controller;

import com.rodtech.qideasregisterapi.model.User;
import com.rodtech.qideasregisterapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email){
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid User entity){
        User newUSer = userService.create(entity);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(newUSer.getId())
                .toUri();

        return ResponseEntity.created(uri).body(newUSer);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid User entity){
        userService.update(entity);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

package com.simsekali.awssqsdemo.controller;

import com.simsekali.awssqsdemo.controller.dto.UserCreateRequest;
import com.simsekali.awssqsdemo.controller.dto.UserCreateResponse;
import com.simsekali.awssqsdemo.controller.dto.UserDto;
import com.simsekali.awssqsdemo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserCreateResponse> registerUser(@Valid @RequestBody UserCreateRequest request) {
        return ResponseEntity.ok(userService.registerUser(request));
    }

    @GetMapping
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam String email) {
        UserDto user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }
}

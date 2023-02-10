package com.tasko.task.interfaces.web.user;

import com.fasterxml.jackson.databind.node.TextNode;
import com.tasko.task.domain.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = {"http://www.tasko.link","http://localhost:3000","http://localhost:3001","http://localhost:3002"})
public class UserController {
    private final UserService userService;

    // Login
    @PostMapping("/api/google/tokensignin")
    public ResponseEntity<?> googleTokenLogin(@RequestBody TextNode tokenDtoString) {
        UserLoginResponseDto responseDto = userService.googleTokenLogin(tokenDtoString.asText());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // Get User Info
    @PostMapping("/api/user")
    public ResponseEntity<?> fetchUserData(@RequestBody TextNode userIdString) {
        UserResponseDto responseDto = userService.fetchUserInfo(userIdString.asText());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}

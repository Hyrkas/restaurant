package com.aikka.restaurant.rest;

import com.aikka.restaurant.model.User;
import com.aikka.restaurant.service.JwtService;
import com.aikka.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        path = "user/",
        produces = "application/json",
        consumes = "application/json"
)
public class UserResource {

    @Autowired
    UserService userService;
    @Autowired
    JwtService jwtService;

    @GetMapping
    public User getUserProfile() {
        return userService.fetchProfile();
    }

    @PostMapping(path = "register/")
    public ResponseEntity<?> postUserAccount(@RequestBody User user) {
        User validUser = userService.fetchUserByUserNameAndPassword(user.getUsername(), user.getPassword());
        if (validUser == null) {
           validUser = userService.createUser(user);
        }
        Integer userId = validUser.getId();
        String jwt = jwtService.issueToken(userId);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .body(validUser);
    }

    @PostMapping(path = "login/")
    public ResponseEntity<?> postUserLogin(@RequestBody User user) {
        User validUser = userService.fetchUserByUserNameAndPassword(user.getUsername(), user.getPassword());
        if (validUser != null) {
            Integer userId = validUser.getId();
            String jwt = jwtService.issueToken(userId);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                    .body(validUser);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

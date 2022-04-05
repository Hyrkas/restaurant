package com.aikka.restaurant.rest;

import com.aikka.restaurant.filter.RestaurantUserPrinciple;
import com.aikka.restaurant.model.User;
import com.aikka.restaurant.service.jwt.JwtService;
import com.aikka.restaurant.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "api for registering new account, login and fetching user profile")
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
    @Autowired
    RestaurantUserPrinciple userPrinciple;

    @GetMapping(
            produces = "application/json",
            consumes = "application/json")
    @PreAuthorize("hasAuthority('USER')")
    @Parameters(value = {
            @Parameter(
                    description = "Bearer Access token",
                    name = "Authorization",
                    schema = @Schema(type = "string"),
                    in = ParameterIn.HEADER,
                    required = true
            ),
            @Parameter(
                    description = "content type header",
                    name = "content-type",
                    schema = @Schema(type = "string"),
                    in = ParameterIn.HEADER,
                    required = true
            )
    })
    public User getUserProfile() {
        return userService.fetchProfile(userPrinciple.getUserId());
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

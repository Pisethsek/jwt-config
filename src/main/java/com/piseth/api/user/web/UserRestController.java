package com.piseth.api.user.web;

import com.piseth.api.user.UserService;
import com.piseth.base.BaseRest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserRestController {

    private final UserService userService;

    // find all users by pagination
    @GetMapping
    public BaseRest<?> findAllUsers(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                    @RequestParam(name = "limit", required = false, defaultValue = "4") Integer limit){
        var users = userService.findAllUsers(page, limit);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("All Users Has Been Retrieved Successfully")
                .timestamp(LocalDateTime.now())
                .data(users)
                .build();
    }

    //find user by id
    @GetMapping("/{id}")
    public BaseRest<?> findUserById(@PathVariable("id") Integer id){
        var user = userService.findUserById(id);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User Has Been Found Successfully")
                .timestamp(LocalDateTime.now())
                .data(user)
                .build();
    }

    //add new user
    @PostMapping
    public BaseRest<?> addNewUser(@RequestBody UserPostDto userPostDto){
        var user = userService.addNewUser(userPostDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User Has Been Created Successfully")
                .timestamp(LocalDateTime.now())
                .data(user)
                .build();
    }

    //update user
    @PutMapping("/{id}")
    public BaseRest<?> updateUserById(@PathVariable("id") Integer id, @RequestBody UserPostDto userPostDto){
        var user = userService.updateUserById(id, userPostDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User Has Been Updated Successfully")
                .timestamp(LocalDateTime.now())
                .data(user)
                .build();
    }

    //delete user
    @PatchMapping("/{id}")
    public BaseRest<?> deleteUserById(@PathVariable("id") Integer id, @RequestBody IsDeletedDto dto){
        var userId = userService.deleteUserById(id, dto.status());
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User Has Been Deleted Successfully")
                .timestamp(LocalDateTime.now())
                .data(userId)
                .build();
    }


}

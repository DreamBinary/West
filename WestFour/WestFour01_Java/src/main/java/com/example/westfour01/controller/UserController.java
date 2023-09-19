package com.example.westfour01.controller;

import com.example.westfour01.entity.User;
import com.example.westfour01.response.UserResponseCode;
import com.example.westfour01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author CXQ
 * @date 2022/1/26
 */

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private final UserService service = new UserService();

    @PostMapping("/signIn")
    public UserResponseCode signIn(@RequestBody User user) {
        if (service.exist(user)) {
            if (service.findMatchUser(user)) {
                return  UserResponseCode.SIGN_IN_SUCCESS;
            } else {
                return UserResponseCode.ERROR_PASSWORD;
            }
        }
        return UserResponseCode.USER_NO_EXIST;
    }

    @PostMapping("/signUp")
    public UserResponseCode signUp(@RequestBody User user) {
        if (service.exist(user)) {
            return UserResponseCode.USER_EXISTED;
        }
        service.insert(user);
        return UserResponseCode.SIGN_UP_SUCCESS;
    }

    @PutMapping("/update")
    public UserResponseCode update(@RequestBody User user) {
        return service.update(user) ? UserResponseCode.UPDATE_SUCCESS : UserResponseCode.UPDATE_FAILED;
    }

    @DeleteMapping("/delete")
    public UserResponseCode delete(@RequestParam String username) {
        return service.delete(username) ? UserResponseCode.DELETE_SUCCESS : UserResponseCode.DELETE_FAILED;
    }
}

package ru.itmentor.spring.boot_security.demo.restController;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;
import ru.itmentor.spring.boot_security.demo.util.UserErrorResponse;
import ru.itmentor.spring.boot_security.demo.util.UserNotFoundException;

import java.util.List;

@RestController
@RequestMapping("api/admin/users")
public class RestAdminController {

    private final UserService userService;

    public RestAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        return allUsers;
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable long id) {
        User user = userService.getUserById(id);
        return user;
    }

    @PostMapping()
    public User createNewUser(@RequestBody User user) {
       return userService.addUser(user);
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable long id, @RequestBody User user) {
        userService.updateUser(id, user);
        return "User with id \" + id + \" update";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return "User with id " + id + " delete";
    }


    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleExeption(UserNotFoundException e){
        UserErrorResponse response = new UserErrorResponse(
                "User with this id wasn't found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}

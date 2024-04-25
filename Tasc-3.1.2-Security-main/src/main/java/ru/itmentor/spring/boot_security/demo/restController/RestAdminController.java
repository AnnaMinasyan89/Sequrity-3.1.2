package ru.itmentor.spring.boot_security.demo.restController;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserServiceImpl;
import ru.itmentor.spring.boot_security.demo.util.UpdateUserException;
import ru.itmentor.spring.boot_security.demo.util.UserErrorResponse;
import ru.itmentor.spring.boot_security.demo.util.UserNotFoundException;

import java.util.List;

@RestController
@RequestMapping("api/admin/users")
public class RestAdminController {

    private final UserServiceImpl userService;

    public RestAdminController(UserServiceImpl userService) {
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
   public ResponseEntity<String> updateUser(@PathVariable long id, @RequestBody User newUser) {
       try {
           userService.updateUser(id, newUser);
           return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
       } catch (UserNotFoundException e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
       } catch (UpdateUserException e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
       } catch (Exception e) {
           return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

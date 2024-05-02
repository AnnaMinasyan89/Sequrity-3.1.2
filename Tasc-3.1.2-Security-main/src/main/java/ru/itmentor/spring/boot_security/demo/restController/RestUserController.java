package ru.itmentor.spring.boot_security.demo.restController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.util.UserNotFoundException;

@RestController
@RequestMapping("api/user")
public class RestUserController {

      @GetMapping()
    public  ResponseEntity<User> getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        if (principal instanceof User) {
            User user = (User) principal;
            return ResponseEntity.ok(user);
        }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}

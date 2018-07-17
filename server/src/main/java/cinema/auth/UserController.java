package cinema.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @CrossOrigin
    @GetMapping("/users")
    public Collection<User> users() {
        return userService.findAll();
    }
}

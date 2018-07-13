package cinema.auth;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping("account")
public class AccountController {

    public static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private UserService userService;

    @CrossOrigin
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User newUser) {
        if (userService.find(newUser.getUsername()) != null) {
            logger.error("username already exist " + newUser.getUsername());
            return new ResponseEntity<>(
                    new CustomErrorType("user with username " + newUser.getUsername() + " already exist "),
                    HttpStatus.CONFLICT
            );
        }
        newUser.setRole("USER");

        return new ResponseEntity<>(userService.save(newUser), HttpStatus.CREATED);
    }

    @CrossOrigin
    @RequestMapping("/login")
    public Principal user(Principal principal) {
        logger.info("user logged " + principal);
        return principal;
    }

    @RequestMapping("/users")
    public Collection<User> users() {
        return userService.findAll();
    }
}

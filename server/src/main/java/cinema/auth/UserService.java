package cinema.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User save(User user) {
        return userRepository.saveAndFlush(user);
    }

    public User find(String userName) {
        return userRepository.findOneByUsername(userName);
    }

    public Collection<User> findAll() {
        return userRepository.findAll();
    }
}

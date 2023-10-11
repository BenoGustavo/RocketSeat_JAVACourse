package none.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class userController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        // Check if the user already exists
        var user = this.userRepository.findByUsername(userModel.getUsername());

        // If the user already exists, return a bad request
        if (user != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }

        // If the user doesn't exist, create a new user
        var userCreated = this.userRepository.save(userModel);

        // Return the user created
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}

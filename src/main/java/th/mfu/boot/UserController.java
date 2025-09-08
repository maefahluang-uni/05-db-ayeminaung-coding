package th.mfu.boot;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    public UserRepository repo;
   
    @PostMapping("/users")
    public ResponseEntity<String> registerUser(@RequestBody User user) {

        if(repo.findByUsername(user.getUsername()) != null) {
            return new ResponseEntity<String>("Username already exists", HttpStatus.BAD_REQUEST);
        }
       
        repo.save(user);

        return new ResponseEntity<String>( "User created",HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> list() {
        
        List<User> users = repo.findAll();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        
        if(repo.existsById(id) == false) {
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        }
       
        repo.deleteById(id);
        return new ResponseEntity<String>("User deleted", HttpStatus.NO_CONTENT);
    }


}

package lacerda.luhan.controller;

import lacerda.luhan.entity.User;
import lacerda.luhan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    private ResponseEntity<?> index() throws IOException, URISyntaxException {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> findOne(@PathVariable(required = true) Long id) {
        Optional<User> findById = userRepository.findById(id);

        if (findById.isPresent())
            return ResponseEntity.ok(findById.get());

        return ResponseEntity.notFound().build();
    }

}

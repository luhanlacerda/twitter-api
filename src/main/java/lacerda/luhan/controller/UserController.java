package lacerda.luhan.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lacerda.luhan.entity.User;
import lacerda.luhan.repository.UserRepository;

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

	@PutMapping("/follow/{userId}/{anotherUserId}")
	private ResponseEntity<?> follow(@PathVariable("userId") Long userId,
			@PathVariable("anotherUserId") Long anotherUserId) {

		Optional<User> findById = userRepository.findById(userId);

		if (!findById.isPresent())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");

		User user = findById.get();
		Optional<User> anotherUserById = userRepository.findById(anotherUserId);

		if (!anotherUserById.isPresent())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário a ser seguido não encontrado");

		User anotherUser = anotherUserById.get();

		user.addFollowee(anotherUser);

		userRepository.save(user);

		Optional<User> user2 = userRepository.findById(user.getId());

		return ResponseEntity.ok().build();
	}

	@PostMapping("/unfollow")
	private ResponseEntity<?> unfollow(@RequestBody Long userId, Long anotherUserId) {

		Optional<User> findById = userRepository.findById(userId);

		if (!findById.isPresent())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");

		User user = findById.get();
		Optional<User> anotherUserById = userRepository.findById(anotherUserId);

		if (!anotherUserById.isPresent())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário a ser seguido não encontrado");

		user.removeFollowee(anotherUserById.get());

		userRepository.save(user);
		return ResponseEntity.ok().build();
	}

}

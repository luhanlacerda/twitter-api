package lacerda.luhan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lacerda.luhan.entity.User;
import lacerda.luhan.repository.UserRepository;

@RestController
@RequestMapping(path = "/init", produces = MediaType.APPLICATION_JSON_VALUE)
public class InitDBController {

	@Autowired
	UserRepository userRepository;

	@PostMapping
	private ResponseEntity<?> create() {

		User user = new User("Luhan");
		User user2 = new User("Igor");

		userRepository.save(user);
		userRepository.save(user2);

		return ResponseEntity.ok().build();
	}

}

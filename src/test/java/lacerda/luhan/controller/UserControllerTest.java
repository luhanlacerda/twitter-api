package lacerda.luhan.controller;

import lacerda.luhan.TwitterApiApplication;
import lacerda.luhan.dto.UserDTO;
import lacerda.luhan.entity.User;
import lacerda.luhan.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.GET;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {
        TwitterApiApplication.class })
@ActiveProfiles("test")
@AutoConfigureWebTestClient
public class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private User user;

    private HttpEntity<String> entity;

    @Before
    public void before(){
        entity = new HttpEntity<>("body");
        userRepository.deleteAll();
        user = userRepository.save(new User("Luhan"));
    }

    @Test
    public void listUserTest() {
        ResponseEntity<List<User>> res = restTemplate.exchange("/users", GET, entity,
                new ParameterizedTypeReference<List<User>>() {
                });

        List<User> body = res.getBody();

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertThat(body.size(), is(1));

        User firstRow = body.get(0);
        assertEquals(firstRow.getId(), user.getId());
        assertEquals(firstRow.getName(), user.getName());
    }

    @Test
    public void saveUserTest() {

        HttpEntity<User> requestBody = new HttpEntity<>(user);

        ResponseEntity<User> res = restTemplate.exchange("/users", HttpMethod.POST, requestBody,
                new ParameterizedTypeReference<User>() {
                });

        User body = res.getBody();

        List<User> foundUser = (List<User>) userRepository.findAll();

        assertEquals(HttpStatus.OK, res.getStatusCode());
        Assertions.assertThat(foundUser).hasSize(2);
    }

    @Test
    public void saveUserWithoutNameTest() throws Exception {

        UserDTO userDTO = new UserDTO();
        userDTO.setName(null);
        HttpEntity<UserDTO> requestBody = new HttpEntity<>(userDTO);

        ResponseEntity<User> res = restTemplate.exchange("/users", HttpMethod.POST, requestBody,
                new ParameterizedTypeReference<User>() {
                });

        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
    }

    @Test
    public void findUserByIdTest() {
        ResponseEntity<User> res = restTemplate.exchange("/users/1", GET, entity,
                new ParameterizedTypeReference<User>() {
                });

        User body = res.getBody();

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(body.getId(), user.getId());
        assertEquals(body.getName(), user.getName());
    }

    @Test
    public void followUserTest() {
        User anotherUser = userRepository.save(new User("Danilo"));

        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setUserId(user.getId());
        userDTO.setAnotherUserId(anotherUser.getId());

        HttpEntity<UserDTO> requestBody = new HttpEntity<>(userDTO);
        ResponseEntity<User> res = restTemplate.exchange("/users/follow", HttpMethod.PUT, requestBody,
                new ParameterizedTypeReference<User>() {
                });

        Optional<User> response = userRepository.findById(user.getId());

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(response.get().getFollowees().size(), 1L);
    }

    @Test
    public void unfollowUserTest() {
        User anotherUser = userRepository.save(new User("Danilo"));

        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setUserId(user.getId());
        userDTO.setAnotherUserId(anotherUser.getId());

        HttpEntity<UserDTO> requestBody = new HttpEntity<>(userDTO);
        ResponseEntity<User> res = restTemplate.exchange("/users/follow", HttpMethod.PUT, requestBody,
                new ParameterizedTypeReference<User>() {
                });

        Optional<User> response = userRepository.findById(user.getId());

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(response.get().getFollowees().size(), 1L);

        res = restTemplate.exchange("/users/unfollow", HttpMethod.PUT, requestBody,
                new ParameterizedTypeReference<User>() {
                });

        response = userRepository.findById(user.getId());
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(response.get().getFollowees().size(), 0);

    }

}

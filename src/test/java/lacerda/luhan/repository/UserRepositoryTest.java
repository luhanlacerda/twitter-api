package lacerda.luhan.repository;

import static org.assertj.core.api.Assertions.assertThat;
import lacerda.luhan.TwitterApiApplication;
import lacerda.luhan.configuration.H2TestConfig;
import lacerda.luhan.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TwitterApiApplication.class, H2TestConfig.class})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    public void getAllUsersTest() {
        List<User> foundUser = (List<User>) userRepository.findAll();
        assertThat(foundUser).hasSize(0);
    }

    @Test
    public void insertUserTest() {
        User user = new User("Usuário Teste");
        userRepository.save(user);

        List<User> foundUser = (List<User>) userRepository.findAll();
        assertThat(foundUser).hasSize(1);
    }

    @Test
    public void findUserByIdTest() {
        User user = new User("Usuário Teste");
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findById(1L);

        assertThat(foundUser).isNotEmpty();
    }

}

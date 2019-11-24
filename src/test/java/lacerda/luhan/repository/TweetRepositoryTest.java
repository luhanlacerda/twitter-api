package lacerda.luhan.repository;

import lacerda.luhan.TwitterApiApplication;
import lacerda.luhan.configuration.H2TestConfig;
import lacerda.luhan.entity.Tweet;
import lacerda.luhan.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TwitterApiApplication.class, H2TestConfig.class})
public class TweetRepositoryTest {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void cleanUp() {
        tweetRepository.deleteAll();
    }

    @Test
    public void getAllTweetersTest() {
        List<Tweet> foundUser = (List<Tweet>) tweetRepository.findAll();
        assertThat(foundUser).hasSize(0);
    }

    @Test
    public void insertTweetTest() {
        User user = new User("Usuário Teste");
        userRepository.save(user);
        Optional<User> userFound = userRepository.findById(1L);

        Tweet tweet = new Tweet("Mensagem", userFound.get(), new Date());
        tweetRepository.save(tweet);

        List<Tweet> foundTweet = (List<Tweet>) tweetRepository.findAll();
        assertThat(foundTweet).hasSize(1);
    }

    @Test
    public void findTweetByUserIdTest() {
        User user = new User("Usuário Teste");
        userRepository.save(user);
        Optional<User> userFound = userRepository.findById(1L);

        Tweet tweet = new Tweet("Mensagem", userFound.get(), new Date());
        tweetRepository.save(tweet);

        List<Tweet> foundTweet = (List<Tweet>) tweetRepository.findByUserId(1L);
        assertThat(foundTweet).hasSize(1);
    }

}

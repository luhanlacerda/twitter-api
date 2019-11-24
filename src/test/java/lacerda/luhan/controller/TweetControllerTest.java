package lacerda.luhan.controller;

import lacerda.luhan.TwitterApiApplication;
import lacerda.luhan.dto.TweetDTO;
import lacerda.luhan.entity.Tweet;
import lacerda.luhan.entity.User;
import lacerda.luhan.repository.TweetRepository;
import lacerda.luhan.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
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

import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.GET;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {
        TwitterApiApplication.class })
@ActiveProfiles("test")
@AutoConfigureWebTestClient
public class TweetControllerTest {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private Tweet tweet;

    private User user;

    private HttpEntity<String> entity;

    @Before
    public void before(){
        entity = new HttpEntity<>("body");
        tweetRepository.deleteAll();
        userRepository.deleteAll();
        user = userRepository.save(new User("Luhan"));
        tweet = tweetRepository.save(new Tweet("Teste", user, new Date()));
    }

    @Test
    public void listTweetersTest() {
        ResponseEntity<List<Tweet>> res = restTemplate.exchange("/tweeters", GET, entity,
                new ParameterizedTypeReference<List<Tweet>>() {
                });

        List<Tweet> body = res.getBody();

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertThat(body.size(), is(1));

        Tweet firstRow = body.get(0);
        assertEquals(firstRow.getId(), tweet.getId());
    }

    @Test
    public void saveTweetTest() {
        tweetRepository.deleteAll();

        TweetDTO newTweet = new TweetDTO();
        newTweet.setMessage("Teste");
        newTweet.setUserId(user.getId());
        HttpEntity<TweetDTO> requestBody = new HttpEntity<>(newTweet);

        ResponseEntity<Tweet> res = restTemplate.exchange("/tweeters", HttpMethod.POST, requestBody,
                new ParameterizedTypeReference<Tweet>() {
                });

        Tweet body = res.getBody();

        List<Tweet> foundUser = (List<Tweet>) tweetRepository.findAll();

        assertEquals(HttpStatus.OK, res.getStatusCode());
        Assertions.assertThat(foundUser).hasSize(1);
    }

    @Test
    public void getNewsFeedTest() {

        ResponseEntity<List<Tweet>> res = restTemplate.exchange("/tweeters/feed/1", GET, entity,
                new ParameterizedTypeReference<List<Tweet>>() {
                });

        List<Tweet> body = res.getBody();

        List<Tweet> foundUser = (List<Tweet>) tweetRepository.findAll();

        assertEquals(HttpStatus.OK, res.getStatusCode());
        Assertions.assertThat(foundUser).hasSize(1);
    }


}

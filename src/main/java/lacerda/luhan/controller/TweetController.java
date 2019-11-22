package lacerda.luhan.controller;

import lacerda.luhan.dto.TweetDTO;
import lacerda.luhan.entity.Tweet;
import lacerda.luhan.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(path = "/tweeters", produces = MediaType.APPLICATION_JSON_VALUE)
public class TweetController {

    @Autowired
    TweetRepository tweetRepository;

    @PostMapping
    private ResponseEntity<?> create(@Valid @RequestBody TweetDTO tweetDTO) {

        Tweet tweet = buildTweetEntity(new Tweet(), tweetDTO);

        tweetRepository.save(tweet);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getNewsFeed(@PathVariable(required = true) Long userId) {
        Optional<Tweet> findById = tweetRepository.findByUserId(userId);

        if (findById.isPresent())
            return ResponseEntity.ok(findById.get());

        return ResponseEntity.notFound().build();
    }

    private Tweet buildTweetEntity(Tweet tweet, @Valid TweetDTO tweetDTO) {
        tweet.setDate(new Date());
        tweet.setMessage(tweetDTO.getMessage());
        tweet.setUser(tweetDTO.getUser());
        return tweet;
    }

}

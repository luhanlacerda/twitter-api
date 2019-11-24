package lacerda.luhan.controller;

import lacerda.luhan.dto.TweetDTO;
import lacerda.luhan.entity.Tweet;
import lacerda.luhan.entity.User;
import lacerda.luhan.repository.TweetRepository;
import lacerda.luhan.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
Classe de controller responsavel pela criacao de tweet e listagem no feed
 */
@RestController
@RequestMapping(path = "/tweeters", produces = MediaType.APPLICATION_JSON_VALUE)
public class TweetController {

    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    private ResponseEntity<?> index() throws IOException, URISyntaxException {
        return ResponseEntity.ok(tweetRepository.findAll());
    }

    // método para criar o tweet
    @PostMapping
    private ResponseEntity<?> create(@Valid @RequestBody TweetDTO tweetDTO) {

        Tweet tweet = buildTweetEntity(new Tweet(), tweetDTO);

        tweetRepository.save(tweet);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/feed")
    private ResponseEntity<?> getNewsFeed(@Valid @RequestBody TweetDTO tweetDTO) {
        // Pega os tweeters do usuario que foi passado pelo body
        List<Tweet> getAllTweetersByUserId = tweetRepository.findByUserId(tweetDTO.getUserId());

        // Seleciona o usuario para retornar o objeto e ter acesso aos ids das pessoas que ele segue
        Optional<User> userById = userRepository.findById(tweetDTO.getUserId());

        // Faz a consulta ao banco para retornar os users que são seguidos
        List<User> getOthersUsers = userById.get().getFollowees()
                .stream().map(f -> userRepository.findById(f.getId())
                        .get()).collect(Collectors.toList());

        // percorre a lista contendo todos usuarios seguidos, pega os tweeters dos mesmos e adiciona na lista que conterá
        // todos os tweeters
        for (User user : getOthersUsers) {
            getAllTweetersByUserId.addAll(user.getTweets());
        }

        // valida se a lista está vazia, caso não esteja retorna sucesso com a lista preenchida
        if (!getAllTweetersByUserId.isEmpty())
            return ResponseEntity.ok(getAllTweetersByUserId);

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/feed/{userId}")
    private ResponseEntity<?> getNewsFeed2(@PathVariable(name="userId") Long userId) {
        // Pega os tweeters do usuario que foi passado pelo body
        List<Tweet> getAllTweetersByUserId = tweetRepository.findByUserId(userId);

        // Seleciona o usuario para retornar o objeto e ter acesso aos ids das pessoas que ele segue
        Optional<User> userById = userRepository.findById(userId);

        // Faz a consulta ao banco para retornar os tweeters dos users que são seguidos
        List<User> getOthersUsers = userById.get().getFollowees()
                .stream().map(f -> userRepository.findById(f.getId())
                        .get()).collect(Collectors.toList());

        // percorre a lista contendo todos usuarios seguidos, pega os tweeters dos mesmos e adiciona na lista que conterá
        // todos os tweeters
        for (User user : getOthersUsers) {
            getAllTweetersByUserId.addAll(user.getTweets());
        }

        // valida se a lista está vazia, caso não esteja retorna sucesso com a lista preenchida
        if (!getAllTweetersByUserId.isEmpty())
            return ResponseEntity.ok(getAllTweetersByUserId);

        return ResponseEntity.notFound().build();
    }

    // metodo para fazer o build (conversão) do novo tweet com os dados que serão passados pelo body
    private Tweet buildTweetEntity(Tweet tweet, @Valid TweetDTO tweetDTO) {
        tweet.setDate(new Date());
        tweet.setMessage(tweetDTO.getMessage());
        Optional<User> user = userRepository.findById(tweetDTO.getUserId());
        tweet.setUser(user.get());
        return tweet;
    }

}

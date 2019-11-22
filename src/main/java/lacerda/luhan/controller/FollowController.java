package lacerda.luhan.controller;

import lacerda.luhan.dto.FollowDTO;
import lacerda.luhan.entity.Follow;
import lacerda.luhan.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/follow", produces = MediaType.APPLICATION_JSON_VALUE)
public class FollowController {

    @Autowired
    FollowRepository followRepository;

    @PostMapping
    private ResponseEntity<?> follow(@Valid @RequestBody FollowDTO followDTO) {

        Follow follow = buildTweetEntity(new Follow(), followDTO);

        followRepository.save(follow);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/unfollow")
    private ResponseEntity<?> unfollow(@PathVariable(required = true) FollowDTO followDTO) {

        followRepository.unfollow(followDTO.getUser().getId(), followDTO.getAnotherUser().getId());

        return ResponseEntity.ok().build();
    }

    private Follow buildTweetEntity(Follow follow, @Valid FollowDTO followDTO) {
        follow.setUser(followDTO.getUser());
        follow.setAnotherUser(followDTO.getAnotherUser());
        return follow;
    }
}

package lacerda.luhan.repository;

import lacerda.luhan.entity.Tweet;
import org.springframework.data.repository.CrudRepository;

public interface TweetRepository extends CrudRepository<Tweet, Long> {
}

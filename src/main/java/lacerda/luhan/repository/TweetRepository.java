package lacerda.luhan.repository;

import lacerda.luhan.entity.Tweet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TweetRepository extends CrudRepository<Tweet, Long> {

    @Query("SELECT t FROM Tweet t WHERE t.user.id = :userId order by t.date desc")
    Optional<Tweet> findByUserId(@Param("userId") Long userId);
}

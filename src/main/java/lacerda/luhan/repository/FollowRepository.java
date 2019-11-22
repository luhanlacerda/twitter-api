package lacerda.luhan.repository;

import lacerda.luhan.entity.Follow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FollowRepository extends CrudRepository<Follow, Long> {

    @Query("DELETE FROM Follow f WHERE f.user.id = :userId and f.anotherUser.id = :anotherUserId")
    void unfollow(@Param("userId") Long id, @Param("anotherUserId") Long id1);
}

package lacerda.luhan.repository;

import lacerda.luhan.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
	
}

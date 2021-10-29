package hh.swd20.discgolfbag.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {

	public Optional <User> findByUsername(@Param("username") String username);
}

package hh.swd20.discgolfbag.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {

	public Optional<User> findByUsername(@Param("name") String username);
	
	public Optional<User> findByEmail(@Param("email") String email);
	
	@Query(value="select * from User u where u.username like %:keyword%", nativeQuery=true)
	public List <User> findByKeyword(@Param("keyword") String keyword);
}

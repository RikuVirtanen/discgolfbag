package hh.swd20.discgolfbag.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface CategoryRepository extends CrudRepository<Category, Long> {
	
	@RestResource public Optional<Category> findByName(@Param("name") String name);
	
	@RestResource public Optional<Category> findById(@Param("id") Long id);
	
	@Query(value="select * "
			+ "from Category c"
			+ "where c.name like %:keyword% ", 
			nativeQuery=true)
	public Iterable<Category> findByKeyword(@Param("keyword") String keyword);
}

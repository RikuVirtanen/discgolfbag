package hh.swd20.discgolfbag.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface DiscRepository extends CrudRepository<Disc, Long> {

	@RestResource public Optional<Disc> findByName(@Param("name") String name);
	
	@RestResource public Iterable<Disc> findByPlastic(@Param("plastic") String plastic);
	
	@RestResource public Iterable<Disc> findByCompany(@Param("company") String company);
	
	@RestResource public Iterable<Disc> findByCategory(@Param("category") String category);
	
	// @RestResource public Iterable<Disc> findByBag(@Param("bag") String bag);
	
	@Query(value="select * "
			+ "from Disc d "
			+ "where d.name like %:keyword% ", 
			nativeQuery=true)
	public Iterable<Disc> findByKeyword(@Param("keyword") String keyword);
}

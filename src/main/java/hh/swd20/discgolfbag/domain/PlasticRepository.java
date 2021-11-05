package hh.swd20.discgolfbag.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface PlasticRepository extends CrudRepository<Plastic, Long> {
	
	@RestResource public Optional<Plastic> findById(@Param("id") Long id);

	@RestResource public Optional<Plastic> findByName(@Param("name") String name);
	
	@Query(value="select * "
			+ "from Plastic p "
			+ "where p.name like %:keyword% ", 
			nativeQuery=true)
	public Iterable<Plastic> findByKeyword(@Param("keyword") String keyword);
}

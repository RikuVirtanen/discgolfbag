package hh.swd20.discgolfbag.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface BagRepository extends CrudRepository<Bag, Long>{
	
	@RestResource public Optional <Bag> findById(@Param("id") Long id);
	
	@RestResource public Optional <Bag> findBagByUserId(@Param("id") Long userId);
	
	@RestResource public Optional <Bag> findByName(@Param("name") String name);
	
	@Query(value="select * "
			+ "from DGBag b "
			+ "where b.name like %:keyword% ", 
			nativeQuery=true)
	public Iterable<Bag> findByKeyword(@Param("keyword") String keyword);
	
}

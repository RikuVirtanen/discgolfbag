package hh.swd20.discgolfbag.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface DGBagRepository extends CrudRepository<DGBag, Long>{
	
	@RestResource public Optional <DGBag> findById(@Param("id") Long id);
	
	@RestResource public Optional <DGBag> findDGBagByUserId(@Param("id") Long userId);
	
	@RestResource public Optional <DGBag> findByName(@Param("name") String name);
	
	@Query(value="select * "
			+ "from DGBag b "
			+ "where b.name like %:keyword% ", 
			nativeQuery=true)
	public Iterable<DGBag> findByKeyword(@Param("keyword") String keyword);
	
}

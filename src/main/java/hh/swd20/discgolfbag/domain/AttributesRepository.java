package hh.swd20.discgolfbag.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface AttributesRepository extends CrudRepository<Attributes, Long>{
	
	@RestResource public Optional<Attributes> findById(@Param("id") Long id);
	
	@RestResource public Optional<Attributes> findByDisc(@Param("disc") Disc disc);
}

package hh.swd20.discgolfbag.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface DiscRepository extends CrudRepository<Disc, Long> {

	@RestResource
	public Optional<Disc> findByName(@Param("name") String name);
	
	@RestResource
	public List<Disc> findByPlastic(@Param("plastic") String plastic);
	
	@RestResource
	public List<Disc> findByInBag(@Param("true") Boolean inBag);
}

package hh.swd20.discgolfbag.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface CompanyRepository extends CrudRepository<Company, Long> {
	
	@RestResource
	public Optional<Company> findByName(@Param("name") String name);
}

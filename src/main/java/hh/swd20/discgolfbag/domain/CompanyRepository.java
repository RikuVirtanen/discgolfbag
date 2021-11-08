package hh.swd20.discgolfbag.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CompanyRepository extends CrudRepository<Company, Long> {
	
	public Optional<Company> findById(@Param("id") Long id);
	
	public Optional<Company> findByName(@Param("name") String name);
	
	@Query(value="select * "
			+ "from Company c "
			+ "where c.name like %:keyword% ", 
			nativeQuery=true)
	public Iterable<Company> findByKeyword(@Param("keyword") String keyword);
}

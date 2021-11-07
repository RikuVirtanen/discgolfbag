package hh.swd20.discgolfbag.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	
	@RestResource public Optional<Company> findById(@Param("id") Long id);
	
	@RestResource public Optional<Company> findByName(@Param("name") String name);
	
	@Query(value="select * "
			+ "from Company c "
			+ "where c.name like %:keyword% ", 
			nativeQuery=true)
	public Iterable<Company> findByKeyword(@Param("keyword") String keyword);
}

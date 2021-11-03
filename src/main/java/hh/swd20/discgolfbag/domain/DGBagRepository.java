package hh.swd20.discgolfbag.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DGBagRepository extends CrudRepository<DGBag, Long>{
	
	public Optional <DGBag> findById(@Param("id") Long bagId);
	
	public Optional <DGBag> findDGBagByUserId(@Param("id") Long userId);
	
	public DGBag findBagByUserId(Long id);
	
}

package hh.swd20.discgolfbag.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hh.swd20.discgolfbag.domain.DGBag;
import hh.swd20.discgolfbag.domain.DGBagRepository;

@Service
public class DGBagService {
	
	@Autowired private DGBagRepository repository;
	
	public List<DGBag> getAll() {
		return (List<DGBag>) repository.findAll();
	}
	
	public DGBag getById(Long id) {
		return repository.findById(id).get();
	}
	
	public DGBag getDGBagByUserId(Long userId) {
		return repository.findDGBagByUserId(userId).get();
	}
	
	public void save(DGBag bag) {
		repository.save(bag);
	}
	
	public void delete(DGBag bag) {
		repository.delete(bag);
	}

}

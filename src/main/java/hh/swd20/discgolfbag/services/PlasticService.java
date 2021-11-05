package hh.swd20.discgolfbag.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hh.swd20.discgolfbag.domain.Plastic;
import hh.swd20.discgolfbag.domain.PlasticRepository;

@Service public class PlasticService {
	
	@Autowired private PlasticRepository repository;
	
	public List<Plastic> getAll() {
		return (List<Plastic>) repository.findAll();
	}
	
	public Plastic getByName(String name) {
		return repository.findByName(name).get();
	}
	
	public Plastic getById(Long id) {
		return repository.findById(id).get();
	}
	
	public void save(Plastic plastic) {
		repository.save(plastic);
	}
	
	public void delete(Plastic plastic) {
		repository.save(plastic);
	}
}

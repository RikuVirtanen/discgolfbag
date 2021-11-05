package hh.swd20.discgolfbag.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hh.swd20.discgolfbag.domain.Bag;
import hh.swd20.discgolfbag.domain.BagRepository;

@Service public class BagService {
	
	@Autowired private BagRepository repository;
	
	public List<Bag> getAll() {
		return (List<Bag>) repository.findAll();
	}
	
	public Bag getById(Long id) {
		return repository.findById(id).get();
	}
	
	public Bag getByName(String name) {
		return repository.findByName(name).get();
	}
	
	public Bag getBagByUserId(Long userId) {
		return repository.findBagByUserId(userId).get();
	}
	
	public void save(Bag bag) {
		repository.save(bag);
	}
	
	public void delete(Bag bag) {
		repository.delete(bag);
	}

}

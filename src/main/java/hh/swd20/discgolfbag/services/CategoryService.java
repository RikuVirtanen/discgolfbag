package hh.swd20.discgolfbag.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hh.swd20.discgolfbag.domain.Category;
import hh.swd20.discgolfbag.domain.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired private CategoryRepository repository;
	
	public List<Category> getAll() {
		return (List<Category>) repository.findAll();
	}
	
	public Category getById(Long id) {
		return repository.findById(id).get();
	}

}

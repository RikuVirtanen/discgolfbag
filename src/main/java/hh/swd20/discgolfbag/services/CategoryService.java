package hh.swd20.discgolfbag.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hh.swd20.discgolfbag.domain.Category;
import hh.swd20.discgolfbag.domain.CategoryRepository;

@Service public class CategoryService {
	
	@Autowired private CategoryRepository repository;
	
	public List<Category> getAll() {
		return (List<Category>) repository.findAll();
	}
	
	public Category getById(Long id) {
		return repository.findById(id).get();
	}
	
	public Category getByName(String name) {
		return repository.findByName(name).get();
	}
	
	public void save(Category category) {
		repository.save(category);
	}
	
	public void delete(Category category) {
		repository.delete(category);
	}
	
	public List<Category> getCategoriesByKeyword(String keyword) {
		keyword = capitalize(keyword);
		return (List<Category>) repository.findByKeyword(keyword);
	}
	
	public String capitalize(String word) {
		String words[] = word.split("\\s");
		String outcome = "";
		for (String w: words) {
			String first = w.substring(0, 1);
			String rest = w.substring(1);
			outcome += first.toUpperCase() + rest.toLowerCase() + " ";
		}
		
		return outcome.trim();
	}

}

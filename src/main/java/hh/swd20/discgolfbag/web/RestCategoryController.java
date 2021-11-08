package hh.swd20.discgolfbag.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hh.swd20.discgolfbag.domain.Category;
import hh.swd20.discgolfbag.domain.CategoryRepository;

@CrossOrigin
@RestController
@RequestMapping(value = "/api", method = RequestMethod.GET)
public class RestCategoryController {

	@Autowired private CategoryRepository repository;
	
	@GetMapping("/categories")
	public List<Category> getCategoriesRest() {
		return (List<Category>) repository.findAll();
	}
	
	@GetMapping("/categories/{id}")
	public Optional<Category> findCategoryRest(@PathVariable("id") Long categoryId) {
		return repository.findById(categoryId);
	}
	
	@RequestMapping(value = "/api/categories", method = RequestMethod.POST)
	public @ResponseBody void saveRest(@RequestBody Category category) {
		repository.save(category);
	}
}

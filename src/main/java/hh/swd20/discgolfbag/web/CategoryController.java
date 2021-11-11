package hh.swd20.discgolfbag.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import hh.swd20.discgolfbag.domain.Category;
import hh.swd20.discgolfbag.domain.CategoryRepository;
import hh.swd20.discgolfbag.domain.Disc;

@CrossOrigin
@Controller
@RequestMapping(value = "/categories")
public class CategoryController {
	
	@Autowired private CategoryRepository repository;
	
	@PreAuthorize(value="hasAuthority('ADMIN')")
	@GetMapping("")
	public String categoryList(Model model) {
		model.addAttribute("categories", repository.findAll());
		model.addAttribute("category", new Category());
		return "categorylist";
	}
	
	@GetMapping("/addcategory")
	public String addCategory(Model model) {
		model.addAttribute("category", new Category());
		return "addcategory"; //thymeleaf template
	}
	
	@PreAuthorize(value="hasAuthority('ADMIN')")
	@GetMapping("/edit/{id}")
	public String editCategory(@PathVariable("id") Long categoryId, Model model) {
		model.addAttribute("category", repository.findById(categoryId));
		return "editcategory"; //thymeleaf template
	}
	
	@PreAuthorize(value="hasAuthority('ADMIN')")
	@GetMapping("/delete/{id}")
	public String deleteCategory(@PathVariable("id") Long categoryId) {
		Category category = repository.findById(categoryId).get();
		List<Disc> discs = category.getDiscs();
		for(Disc disc : discs) {
			disc.setCategory(null);
		}
		repository.delete(category);
		return "redirect:/categories";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String saveCategory(@ModelAttribute Category category) {
		if(!repository.findByName(category.capitalize(category.getName())).isEmpty()) {
			return "redirect:/categories";
		}
		category.setName(category.capitalize(category.getName()));
		repository.save(category);
		return "redirect:/categories";
	}
}

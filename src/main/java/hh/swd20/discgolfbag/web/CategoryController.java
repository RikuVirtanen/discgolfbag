package hh.swd20.discgolfbag.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.swd20.discgolfbag.domain.Category;
import hh.swd20.discgolfbag.domain.CategoryRepository;
import hh.swd20.discgolfbag.domain.Disc;
import hh.swd20.discgolfbag.services.CategoryService;

@CrossOrigin
@Controller
public class CategoryController {
	
	@Autowired private CategoryRepository repository;
	@Autowired private CategoryService categoryService;
	
	/*********************** RESTFUL SERVICES *********************************/
	
	@RequestMapping(value = "/api/categories", method = RequestMethod.GET)
	public @ResponseBody List<Category> getCategoriesRest() {
		return (List<Category>) repository.findAll();
	}
	
	@RequestMapping(value = "/api/categories/{id}", method = RequestMethod.GET)
	public @ResponseBody Optional<Category> findCategoryRest(@PathVariable("id") Long categoryId) {
		return repository.findById(categoryId);
	}
	
	/************************************************************************/
	
	@PreAuthorize(value="hasAuthority('ADMIN')")
	@RequestMapping(value="/categories", method = RequestMethod.GET)
	public String categoryList(Model model) {
		model.addAttribute("categories", categoryService.getAll());
		model.addAttribute("category", new Category());
		return "categorylist";
	}
	
	@RequestMapping(value="/categories/addcategory", method=RequestMethod.GET)
	public String addCategory(Model model) {
		model.addAttribute("category", new Category());
		return "addcategory"; //thymeleaf template
	}
	
	@RequestMapping(value="/categories/save", method=RequestMethod.POST)
	public String saveCategory(@ModelAttribute Category category) {
		if(categoryService.getByName(category.capitalize(category.getName())) != null) {
			return "redirect:/categories";
		}
		category.setName(category.capitalize(category.getName()));
		repository.save(category);
		return "redirect:/categories";
	}
	
	@PreAuthorize(value="hasAuthority('ADMIN')")
	@RequestMapping(value="/categories/editcategory/{id}", method=RequestMethod.GET)
	public String editCategory(@PathVariable("id") Long categoryId, Model model) {
		model.addAttribute("category", categoryService.getById(categoryId));
		return "editcategory"; //thymeleaf template
	}
	
	@PreAuthorize(value="hasAuthority('ADMIN')")
	@RequestMapping(value="/categories/delete/{id}", method=RequestMethod.GET)
	public String deleteCategory(@PathVariable("id") Long categoryId) {
		Category category = categoryService.getById(categoryId);
		List<Disc> discs = category.getDiscs();
		for(Disc disc : discs) {
			disc.setCategory(null);
		}
		repository.delete(category);
		return "redirect:/categories";
	}
}

package hh.swd20.discgolfbag.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.swd20.discgolfbag.domain.Category;
import hh.swd20.discgolfbag.services.CategoryService;

@CrossOrigin
@Controller
public class CategoryController {
	
	@Autowired private CategoryService categoryService;
	
	/*********************** RESTFUL SERVICES *********************************/
	
	@RequestMapping(value = "/api/categories", method = RequestMethod.GET)
	public @ResponseBody List<Category> getCategoriesRest() {
		return categoryService.getAll();
	}
	
	@RequestMapping(value = "/api/categories/{id}", method = RequestMethod.GET)
	public @ResponseBody Category findCategoryRest(@PathVariable("id") Long categoryId) {
		return categoryService.getById(categoryId);
	}
}

package hh.swd20.discgolfbag.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hh.swd20.discgolfbag.domain.Bag;
import hh.swd20.discgolfbag.domain.BagRepository;
import hh.swd20.discgolfbag.domain.Disc;
import hh.swd20.discgolfbag.domain.DiscRepository;
import hh.swd20.discgolfbag.services.BagService;
import hh.swd20.discgolfbag.services.CategoryService;
import hh.swd20.discgolfbag.services.CompanyService;
import hh.swd20.discgolfbag.services.DiscService;
import hh.swd20.discgolfbag.services.PlasticService;
import hh.swd20.discgolfbag.services.UserService;

@CrossOrigin
@Controller
@RequestMapping("/discs")
public class DiscController {
	
	@Autowired private DiscRepository repository;
	@Autowired private BagRepository bagRepository;
	
	@Autowired private DiscService discService;
	@Autowired private UserService userService;
	@Autowired private PlasticService plasticService;
	@Autowired private CategoryService categoryService;
	@Autowired private CompanyService companyService;
	@Autowired private BagService bagService;
	
	@GetMapping("")
	public String storage(Model model, String keyword, Authentication auth) {
		if(keyword != null ) {
			model.addAttribute("discs", repository.findByKeyword(keyword.toLowerCase()));
		} else {
			model.addAttribute("discs", discService.getDiscs());
		}
		model.addAttribute("bag", bagService.getBagByUserId(userService.getByUsername(auth.getName()).getId()).getDiscs());
		return "storage";
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@PostMapping("/save")
	public String saveDisc(@ModelAttribute Disc disc) {
		// saves new disc with its info
		disc.setName(disc.capitalize(disc.getName()));
		disc.setCategory(disc.getCategory());
		disc.setCompany(disc.getCompany());
		disc.setPlastic(disc.getPlastic());
		repository.save(disc);
		
		return "redirect:/discs";
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@GetMapping("/add")
	public String addNewDisc(Model model) {
		model.addAttribute("disc", new Disc());
		model.addAttribute("categories", categoryService.getAll());
		model.addAttribute("companies", companyService.getAll());
		model.addAttribute("plastics", plasticService.getAll());
		
		return "discadd";
	}
	
	@PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
	@PostMapping("/addtobag/{id}")
	public String addDiscToBag(@PathVariable("id") Long discId, Authentication auth) {
		Disc disc = discService.getById(discId);
		Long userId = userService.getByUsername(auth.getName()).getId();
		Bag bag = bagService.getBagByUserId(userId);
		if (!bag.getDiscs().contains(disc)) {
			disc.addToBag(bag);
			repository.save(disc);
			bag.addDisc(disc);
			bagRepository.save(bag);
		} 
		
		return "redirect:/discs";
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@GetMapping("/edit/{id}")
	public String editDisc(@PathVariable("id") Long discId, Model model) {
		model.addAttribute("disc", discService.getById(discId));
		model.addAttribute("categories", categoryService.getAll());
		model.addAttribute("companies", companyService.getAll());
		model.addAttribute("plastics", plasticService.getAll());
		return "editdisc";
	}
	
	// deletes disc entity and its representations from other entities
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@GetMapping("/delete/{id}")
	public String deleteDisc(@PathVariable("id") Long discId) {
		Disc disc = discService.getById(discId);
		List<Bag> bags = bagService.getAll();
		for(Bag b: bags) {
			if (b.getDiscs().contains(disc)) {
				b.getDiscs().remove(disc);
				bagService.save(b);
			}
		}
		discService.delete(disc);
		return "redirect:/discs";
	}
	
}

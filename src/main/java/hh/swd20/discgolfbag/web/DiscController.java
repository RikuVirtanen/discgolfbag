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
import hh.swd20.discgolfbag.domain.CategoryRepository;
import hh.swd20.discgolfbag.domain.CompanyRepository;
import hh.swd20.discgolfbag.domain.Disc;
import hh.swd20.discgolfbag.domain.DiscRepository;
import hh.swd20.discgolfbag.domain.PlasticRepository;
import hh.swd20.discgolfbag.domain.UserRepository;

@CrossOrigin
@Controller
@RequestMapping("/discs")
public class DiscController {
	
	@Autowired private DiscRepository repository;
	@Autowired private BagRepository bagRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private CategoryRepository categoryRepository;
	@Autowired private CompanyRepository companyRepository;
	@Autowired private PlasticRepository plasticRepository;
	
	@GetMapping("")
	public String storage(Model model, String keyword, Authentication auth) {
		if(keyword != null ) {
			model.addAttribute("discs", repository.findByKeyword(keyword.toLowerCase()));
		} else {
			model.addAttribute("discs", repository.findAll());
		}
		model.addAttribute("bag", bagRepository.findBagByUserId(userRepository.findByUsername(auth.getName()).getId()).get().getDiscs());
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
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("companies", companyRepository.findAll());
		model.addAttribute("plastics", plasticRepository.findAll());
		
		return "discadd";
	}
	
	@PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
	@GetMapping("/addtobag/{id}")
	public String addDiscToBag(@PathVariable("id") Long discId, Authentication auth) {
		Disc disc = repository.findById(discId).get();
		Long userId = userRepository.findByUsername(auth.getName()).getId();
		Bag bag = bagRepository.findBagByUserId(userId).get();
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
		model.addAttribute("disc", repository.findById(discId));
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("companies", companyRepository.findAll());
		model.addAttribute("plastics", plasticRepository.findAll());
		return "editdisc";
	}
	
	// deletes disc entity and its representations from other entities
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@GetMapping("/delete/{id}")
	public String deleteDisc(@PathVariable("id") Long discId) {
		Disc disc = repository.findById(discId).get();
		List<Bag> bags = (List<Bag>) bagRepository.findAll();
		for(Bag b: bags) {
			if (b.getDiscs().contains(disc)) {
				b.getDiscs().remove(disc);
				bagRepository.save(b);
			}
		}
		repository.delete(disc);
		return "redirect:/discs";
	}
	
}

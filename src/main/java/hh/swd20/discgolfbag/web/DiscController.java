package hh.swd20.discgolfbag.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.swd20.discgolfbag.domain.Bag;
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
public class DiscController {
	
	@Autowired private DiscRepository repository;
	
	@Autowired private DiscService discService;
	@Autowired private UserService userService;
	@Autowired private PlasticService plasticService;
	@Autowired private CategoryService categoryService;
	@Autowired private CompanyService companyService;
	@Autowired private BagService bagService;
	
	@RequestMapping(value = "/discs/storage", method = RequestMethod.GET)
	public String storage(Model model, String keyword, Authentication auth) {
		if(keyword != null ) {
			model.addAttribute("discs", repository.findByKeyword(keyword.toLowerCase()));
		} else {
			model.addAttribute("discs", discService.getDiscs());
		}
		model.addAttribute("bag", bagService.getBagByUserId(userService.getByUsername(auth.getName()).getId()).getDiscs());
		return "storage";
	}
	
	/******************RESTFUL SERVICES **************************/
	
	@RequestMapping(value = "/discs", method = RequestMethod.GET)
	public @ResponseBody List<Disc> discListRest() {
		return (List<Disc>) repository.findAll();
	}
	
	@RequestMapping(value = "/discs/{id}", method = RequestMethod.GET)
	public @ResponseBody Optional<Disc> findDiscRest(@PathVariable("id") Long discId) {
		return repository.findById(discId);
	}
	
	@RequestMapping(value = "/discs/{name}", method = RequestMethod.GET)
	public @ResponseBody Optional<Disc> findDiscRest(@PathVariable("name") String discName) {
		return repository.findByName(discName);
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@RequestMapping(value = "/discs", method = RequestMethod.POST)
	public @ResponseBody void saveDiscRest(@RequestBody Disc disc) {
		repository.save(disc);
	}
	
	/*************************************************************/
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@RequestMapping(value = "/discs/save", method = RequestMethod.POST)
	public String saveDisc(@ModelAttribute Disc disc) {
		// saves new disc with its info
		disc.setName(disc.capitalize(disc.getName()));
		disc.setCategory(disc.getCategory());
		disc.setCompany(disc.getCompany());
		disc.setPlastic(disc.getPlastic());
		discService.save(disc);
		
		return "redirect:/discs/storage";
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@RequestMapping(value = "/discs/storage/add", method = RequestMethod.GET)
	public String addNewDisc(Model model) {
		model.addAttribute("disc", new Disc());
		model.addAttribute("categories", categoryService.getAll());
		model.addAttribute("companies", companyService.getAll());
		model.addAttribute("plastics", plasticService.getAll());
		
		return "discadd";
	}
	
	@PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
	@RequestMapping(value = "/discs/storage/addtobag/{discid}", method = RequestMethod.GET)
	public String addDiscToBag(@PathVariable("discid") Long discId, Authentication auth) {
		Disc disc = discService.getById(discId);
		Long userId = userService.getByUsername(auth.getName()).getId();
		Bag bag = bagService.getBagByUserId(userId);
		if (!bag.getDiscs().contains(disc)) {
			disc.addToBag(bag);
			discService.save(disc);
			bag.addDisc(disc);
			bagService.save(bag);
		} 
		
		return "redirect:/discs/storage";
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@RequestMapping(value = "/discs/storage/editdisc/{id}", method = RequestMethod.GET)
	public String editDisc(@PathVariable("id") Long discId, Model model) {
		model.addAttribute("disc", discService.getById(discId));
		model.addAttribute("categories", categoryService.getAll());
		model.addAttribute("companies", companyService.getAll());
		model.addAttribute("plastics", plasticService.getAll());
		return "editdisc";
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@RequestMapping(value = "/discs/storage/deletedisc/{id}", method = RequestMethod.GET)
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
		return "redirect:/discs/storage";
	}
	
}

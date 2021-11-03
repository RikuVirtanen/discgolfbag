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

import hh.swd20.discgolfbag.domain.CategoryRepository;
import hh.swd20.discgolfbag.domain.CompanyRepository;
import hh.swd20.discgolfbag.domain.DGBag;
import hh.swd20.discgolfbag.domain.DGBagRepository;
import hh.swd20.discgolfbag.domain.Disc;
import hh.swd20.discgolfbag.domain.DiscRepository;
import hh.swd20.discgolfbag.domain.UserRepository;

@CrossOrigin
@Controller
public class DiscController {
	
	@Autowired
	private DiscRepository dRepository;
	
	@Autowired 
	private CategoryRepository catRepository;
	
	@Autowired 
	private CompanyRepository comRepository;
	
	@Autowired
	private DGBagRepository bagRepository;
	
	@Autowired UserRepository userRepository;
	
	@RequestMapping(value = "/storage", method = RequestMethod.GET)
	public String listDiscs(Model model) {
		model.addAttribute("discs", dRepository.findAll());
		return "storage";
	}
	
	/******************RESTFUL SERVICES **************************/
	
	@RequestMapping(value = "/discs", method = RequestMethod.GET)
	public @ResponseBody List<Disc> discList() {
		return (List<Disc>) dRepository.findAll();
	}
	
	@RequestMapping(value = "/discs/{id}", method = RequestMethod.GET)
	public @ResponseBody Optional<Disc> findDiscRest(@PathVariable("id") Long discId) {
		return dRepository.findById(discId);
	}
	
	@RequestMapping(value = "/discs/{name}", method = RequestMethod.GET)
	public @ResponseBody Optional<Disc> findDiscRest(@PathVariable("name") String discName) {
		return dRepository.findByName(discName);
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@RequestMapping(value = "/discs", method = RequestMethod.POST)
	public @ResponseBody Disc saveDiscRest(@RequestBody Disc disc) {
		return dRepository.save(disc);
	}
	
	/*************************************************************/
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveDisc(@ModelAttribute Disc disc) {
		disc.setName(disc.capitalize(disc.getName()));
		disc.setPlastic(disc.capitalize(disc.getPlastic()));
		dRepository.save(disc);
		return "redirect:/storage";
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@RequestMapping(value = "/storage/discadd", method = RequestMethod.GET)
	public String addNewDisc(Model model) {
		model.addAttribute("disc", new Disc());
		model.addAttribute("categories", catRepository.findAll());
		model.addAttribute("companies", comRepository.findAll());
		return "discadd";
	}
	
	@PreAuthorize(value = "hasAuthority('USER')")
	@RequestMapping(value = "/storage/add/{discid}", method = RequestMethod.GET)
	public String addDiscToBag(@PathVariable("discid") Long discId, Authentication auth) {
		Disc disc = dRepository.findById(discId).get();
		Long userId = userRepository.findByUsername(auth.getName()).getId();
		DGBag bag = bagRepository.findDGBagByUserId(userId).get();
		disc.addToBag(bag);
		dRepository.save(disc);
		bag.addDisc(disc);
		bagRepository.save(bag);
		return "redirect:/storage";
	}
	
	@PreAuthorize(value = "hasAuthority('USER')")
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public String removeDisc(@PathVariable("id") Long discId, Authentication auth) {
		Disc disc = dRepository.findById(discId).get();
		dRepository.save(disc);
		Long userId = userRepository.findByUsername(auth.getName()).getId();
		DGBag bag = bagRepository.findDGBagByUserId(userId).get();
		bag.getDiscs().remove(disc);
		bagRepository.save(bag);
		return "redirect:/mybag";
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@RequestMapping(value = "/storage/edit/{id}", method = RequestMethod.GET)
	public String editDisc(@PathVariable("id") Long discId, Model model) {
		model.addAttribute("disc", dRepository.findById(discId).get());
		model.addAttribute("categories", catRepository.findAll());
		model.addAttribute("companies", comRepository.findAll());
		return "editdisc";
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@RequestMapping(value = "storage/delete/{id}", method = RequestMethod.GET)
	public String deleteDisc(@PathVariable("id") Long discId) {
		dRepository.deleteById(discId);
		return "redirect:/storage";
	}
	
}

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.swd20.discgolfbag.domain.DGBag;
import hh.swd20.discgolfbag.domain.DGBagRepository;
import hh.swd20.discgolfbag.domain.Disc;
import hh.swd20.discgolfbag.domain.UserRepository;

@CrossOrigin
@Controller
public class DGBagController {

	@Autowired
	private DGBagRepository repository;
	
	@Autowired
	private UserRepository userRepository;
	
	/************************* RESTFUL SERVICES ****************************/
	
	@RequestMapping(value="/api/bags", method = RequestMethod.GET)
	public @ResponseBody List<DGBag> getDGBagsRest() {
		return (List<DGBag>) repository.findAll();
	}
	
	@RequestMapping(value="/api/bag/{id}", method = RequestMethod.GET)
	public @ResponseBody Optional<DGBag> findDGBagRest(@PathVariable("id") Long bagId) {
		return repository.findById(bagId);
	}
	
	/**********************************************************************/
	
	@RequestMapping(value = "/mybag", method = RequestMethod.GET)
	public String listMyDiscs(Model model, Authentication auth) {
		Long userId = userRepository.findByUsername(auth.getName()).getId();
		if(repository.findBagByUserId(userId) == null) {
			model.addAttribute("bag", new DGBag());
			model.addAttribute("user", userRepository.findByUsername(auth.getName()));
			return "newbag";
		} else {
			List <Disc> discs = repository.findDGBagByUserId(userId).get().getDiscs();
			model.addAttribute("discs", discs);
			model.addAttribute("user", userRepository.findByUsername(auth.getName()));
		return "bag";
		}
	}
	
	@RequestMapping(value = "/findusersbag/{id}", method = RequestMethod.GET)
	public String findUsersBag(@PathVariable("id") Long userId, Model model) {
		List <Disc> discs = repository.findDGBagByUserId(userId).get().getDiscs();
		model.addAttribute("discs", discs);
		model.addAttribute("user", userRepository.findById(userId).get());
		return "bag";
	}
	
	@PreAuthorize(value = "hasAuthority('USER')")
	@RequestMapping(value="/savebag", method = RequestMethod.POST)
	public String createBag(@ModelAttribute DGBag bag, Authentication auth) {
		bag.setUser(userRepository.findByUsername(auth.getName()));
		repository.save(bag);
		return "redirect:/mybag";
	}
}

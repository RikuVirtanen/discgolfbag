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

import hh.swd20.discgolfbag.domain.Bag;
import hh.swd20.discgolfbag.domain.BagRepository;
import hh.swd20.discgolfbag.domain.Disc;
import hh.swd20.discgolfbag.domain.DiscRepository;
import hh.swd20.discgolfbag.domain.User;
import hh.swd20.discgolfbag.services.BagService;
import hh.swd20.discgolfbag.services.DiscService;
import hh.swd20.discgolfbag.services.UserService;

@CrossOrigin
@Controller
public class BagController {
	
	@Autowired private BagRepository repository;
	@Autowired private DiscRepository discRepository;
	
	@Autowired private UserService userService;
	@Autowired private BagService bagService;
	@Autowired private DiscService discService;
	
	/************************* RESTFUL SERVICES ****************************/
	
	@RequestMapping(value="/bags", method = RequestMethod.GET)
	public @ResponseBody List<Bag> getDGBagsRest() {
		return (List<Bag>) repository.findAll();
	}
	
	@RequestMapping(value="/bags/{id}", method = RequestMethod.GET)
	public @ResponseBody Optional<Bag> findDGBagRest(@PathVariable("id") Long bagId) {
		return repository.findById(bagId);
	}
	
	/**********************************************************************/
	
	@PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
	@RequestMapping(value = {"/bags/mybag", "/bags/mybag/{id}"}, method = RequestMethod.GET)
	public String listMyDiscs(@PathVariable(required = false) Long id, Model model, Authentication auth, String keyword) {
		if (id != null) {
			if(id != userService.getByUsername(auth.getName()).getId()) {
				User user = userService.getById(id);
				model.addAttribute("user", user);
				model.addAttribute("bag", bagService.getBagByUserId(id).getDiscs());
			}
			else {
				User me = userService.getByUsername(auth.getName());
				model.addAttribute("me", me);
				model.addAttribute("bag", bagService.getBagByUserId(me.getId()).getDiscs());
			}
		}
		else {
			User me = userService.getByUsername(auth.getName());
			model.addAttribute("me", me);
			model.addAttribute("bag", bagService.getBagByUserId(me.getId()).getDiscs());
		}
		
		return "bag";
	}
	
	@PreAuthorize(value = "hasAuthority('USER')")
	@RequestMapping(value="/bags/savebag", method = RequestMethod.POST)
	public String createBag(@ModelAttribute Bag bag, Authentication auth) {
		bag.setUser(userService.getByUsername(auth.getName()));
		bagService.save(bag);
		return "redirect:/bags/mybag";
	}
	
	@PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
	@RequestMapping(value = "/bags/remove/{id}", method = RequestMethod.GET)
	public String removeDiscFromBag(@PathVariable("id") Long discId, Authentication auth) {
		Disc disc = discService.getById(discId);
		Long userId = userService.getByUsername(auth.getName()).getId();
		Bag bag = bagService.getBagByUserId(userId);
		bag.getDiscs().remove(disc);
		repository.save(bag);
		disc.removeFromBag(bag);
		discRepository.save(disc);
		return "redirect:/bags/mybag";
	}
}

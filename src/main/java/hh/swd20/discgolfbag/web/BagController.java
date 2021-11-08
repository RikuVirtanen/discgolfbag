package hh.swd20.discgolfbag.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("/bags")
public class BagController {
	
	@Autowired private BagRepository repository;
	@Autowired private DiscRepository discRepository;
	
	@Autowired private UserService userService;
	@Autowired private BagService bagService;
	@Autowired private DiscService discService;
	
	
	@PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
	@GetMapping("/{id}")
	public String listMyDiscs(@PathVariable("id") Long id, Model model, Authentication auth, String keyword) {
		User user = userService.getById(id);
		model.addAttribute("me", userService.getByUsername(auth.getName()));
		model.addAttribute("user", user);
		model.addAttribute("bag", user.getBag());
		return "bag";
	}
	
	@PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
	@PostMapping("/remove/{id}")
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

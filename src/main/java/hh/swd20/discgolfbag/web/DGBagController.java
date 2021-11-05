package hh.swd20.discgolfbag.web;

import java.util.List;

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
import hh.swd20.discgolfbag.domain.Disc;
import hh.swd20.discgolfbag.services.DGBagService;
import hh.swd20.discgolfbag.services.DiscService;
import hh.swd20.discgolfbag.services.UserService;

@CrossOrigin
@Controller
public class DGBagController {
	
	@Autowired private UserService userService;
	@Autowired private DGBagService bagService;
	@Autowired private DiscService discService;
	
	/************************* RESTFUL SERVICES ****************************/
	
	@RequestMapping(value="/api/bags", method = RequestMethod.GET)
	public @ResponseBody List<DGBag> getDGBagsRest() {
		return bagService.getAll();
	}
	
	@RequestMapping(value="/api/bags/{id}", method = RequestMethod.GET)
	public @ResponseBody DGBag findDGBagRest(@PathVariable("id") Long bagId) {
		return bagService.getById(bagId);
	}
	
	/**********************************************************************/
	
	@PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
	@RequestMapping(value = "/bags/mybag", method = RequestMethod.GET)
	public String listMyDiscs(Model model, Authentication auth, String keyword) {
		Long userId = userService.getByUsername(auth.getName()).getId();
		model.addAttribute("user", userService.getByUsername(auth.getName()));
		model.addAttribute("me", userService.getByUsername(auth.getName()));
		List <Disc> discs = bagService.getDGBagByUserId(userId).getDiscs();
		model.addAttribute("discs", discs);	
		model.addAttribute("bag", 
				bagService.getDGBagByUserId(
						userService.getByUsername(
								auth.getName()).getId()).getDiscs()
				);
		return "bag";
	}
	
	@PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
	@RequestMapping(value = "/bags/findusersbag/{id}", method = RequestMethod.GET)
	public String findUsersBag(@PathVariable("id") Long userId, Model model, Authentication auth) {
		if(bagService.getDGBagByUserId(userId) == null) {
			return "redirect:/users";
		} else {
			List <Disc> discs = bagService.getDGBagByUserId(userId).getDiscs();
			model.addAttribute("discs", discs);
			model.addAttribute("user", userService.getById(userId));
			model.addAttribute("me", userService.getByUsername(auth.getName()));
			model.addAttribute("bag", 
					bagService.getDGBagByUserId(
							userService.getByUsername(
									auth.getName()).getId()).getDiscs()
					);
			return "bag";
		}
	}
	
	@PreAuthorize(value = "hasAuthority('USER')")
	@RequestMapping(value="/bags/savebag", method = RequestMethod.POST)
	public String createBag(@ModelAttribute DGBag bag, Authentication auth) {
		bag.setUser(userService.getByUsername(auth.getName()));
		bagService.save(bag);
		return "redirect:/bags/mybag";
	}
	
	@PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
	@RequestMapping(value = "/bags/remove/{id}", method = RequestMethod.GET)
	public String removeDiscFromBag(@PathVariable("id") Long discId, Authentication auth) {
		Disc disc = discService.getById(discId);
		discService.save(disc);
		Long userId = userService.getByUsername(auth.getName()).getId();
		DGBag bag = bagService.getDGBagByUserId(userId);
		bag.getDiscs().remove(disc);
		bagService.save(bag);
		return "redirect:/bags/mybag";
	}
}

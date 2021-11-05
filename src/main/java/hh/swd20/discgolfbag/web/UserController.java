package hh.swd20.discgolfbag.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.swd20.discgolfbag.domain.DGBag;
import hh.swd20.discgolfbag.domain.Disc;
import hh.swd20.discgolfbag.domain.SignupForm;
import hh.swd20.discgolfbag.domain.User;
import hh.swd20.discgolfbag.services.DGBagService;
import hh.swd20.discgolfbag.services.DiscService;
import hh.swd20.discgolfbag.services.UserService;

@Controller
public class UserController {
	
	@Autowired private DGBagService bagService;
	@Autowired private UserService userService;
	@Autowired private DiscService discService;
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signUser(Model model) {
		model.addAttribute("signupform", new SignupForm());
		return "signup";
	}
	
	/********************* RESTFUL SERVICES *********************************/
	
	@RequestMapping(value="/api/users/bag/{id}", method = RequestMethod.GET)
	public @ResponseBody DGBag findDGBagByUserId(@PathVariable("id") Long userId) {
		return bagService.getDGBagByUserId(userId);
	}
	
	@RequestMapping(value="/api/users", method = RequestMethod.GET)
	public @ResponseBody List <User> findUsersRest() {
		return userService.getAll();
	}
	
	@RequestMapping(value="/api/users/{id}", method = RequestMethod.GET)
	public @ResponseBody User findUserById(@PathVariable("id") Long userId) {
		return userService.getById(userId);
	}
	
	@RequestMapping(value="/api/users/username", method = RequestMethod.GET)
	@ResponseBody
	public String currentUsername(Authentication auth) {
		return auth.getName();
	}
	
	/************************************************************************/
	
	@RequestMapping(value = "/users/saveuser", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("signupform") SignupForm signupForm, BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) {
			if(signupForm.getPassword().equals(signupForm.getPasswordCheck())) {
				String pwd = signupForm.getPassword();
				BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
				String hashPwd = bc.encode(pwd);
				
				User newUser = new User();
				newUser.setPasswordHash(hashPwd);
				newUser.setUsername(signupForm.getUsername().toLowerCase());
				newUser.setEmail(signupForm.getEmail());
				newUser.setRole("USER");

				if(userService.findByUsername(newUser.getUsername()).isEmpty()) {
					userService.save(newUser);
					DGBag bag = new DGBag(newUser.getUsername() + "'s bag", "Default", newUser);
					bagService.save(bag);
				}
				else {
					bindingResult.rejectValue("username", "err.username", "Username already exists!");
					return "signup";
				}
			}
			else {
				bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords does not match!");
				return "signup";
			}
		}
		else {
			return "signup";
		}
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/users/savenewuser", method = RequestMethod.POST)
	public String saveNewUser(@Valid @ModelAttribute("signupform") SignupForm signupForm, BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) {
			if(signupForm.getPassword().equals(signupForm.getPasswordCheck())) {
				String pwd = signupForm.getPassword();
				BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
				String hashPwd = bc.encode(pwd);
				
				User newUser = new User();
				newUser.setPasswordHash(hashPwd);
				newUser.setUsername(signupForm.getUsername());
				newUser.setEmail(signupForm.getEmail());
				newUser.setRole(signupForm.getRole());
				if(userService.findByUsername(newUser.getUsername()).isEmpty()) {
					userService.save(newUser);
					DGBag bag = new DGBag(newUser.getUsername() + "'s bag", "Default", newUser);
					bagService.save(bag);
				}
				else {
					bindingResult.rejectValue("username", "err.username", "Username already exists!");
					return "/users/adduser";
				}
			}
			else {
				bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords does not match!");
				return "adduser";
			}
		}
		else {
			return "adduser";
		}
		return "redirect:/userlist";
	}
	
	@RequestMapping(value = "/users/saveolduser", method = RequestMethod.POST)
	public String saveOldUser(@ModelAttribute User user) {
		userService.save(user);
		return "redirect:/users";
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String listUsers(Model model, String keyword) {
		
		if(keyword != null) {
			model.addAttribute("users", userService.findByKeyword(keyword));
		} else {
			model.addAttribute("users", userService.getAll());
		}
		return "userlist";
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@RequestMapping(value="/users/adduser", method = RequestMethod.GET)
	public String addUser(Model model) {
		model.addAttribute("signupform", new SignupForm());
		return "adduser";
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@RequestMapping(value="/users/edituser/{id}", method = RequestMethod.GET)
	public String editUser(@PathVariable("id") Long userId, Model model) {
		model.addAttribute("user", userService.getById(userId));
		return "edituser";
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@RequestMapping(value="/users/deleteuser/{id}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable("id") Long userId, Model model) {
		bagService.delete(bagService.getDGBagByUserId(userId));
		userService.delete(userService.getById(userId));
		return "redirect:/users";
	}
	
	@PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
	@RequestMapping(value = "/users/{userid}/add/{discid}", method = RequestMethod.GET)
	public String addDiscFromUser(@PathVariable("userid") Long userId, @PathVariable("discid") Long discId, Authentication auth, Model model) {
		Disc disc = discService.getById(discId);
		Long myId = userService.getByUsername(auth.getName()).getId();
		DGBag bag = bagService.getDGBagByUserId(myId);
		if (!bag.getDiscs().contains(disc)) {
			disc.addToBag(bag);
			discService.save(disc);
			bag.addDisc(disc);
			bagService.save(bag);
		} 
		model.addAttribute("userid", userId);
		
		return "redirect:/bags/findusersbag/{userid}";
	}
}

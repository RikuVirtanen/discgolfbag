package hh.swd20.discgolfbag.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import hh.swd20.discgolfbag.domain.Bag;
import hh.swd20.discgolfbag.domain.BagRepository;
import hh.swd20.discgolfbag.domain.Disc;
import hh.swd20.discgolfbag.domain.SignupForm;
import hh.swd20.discgolfbag.domain.User;
import hh.swd20.discgolfbag.domain.UserRepository;
import hh.swd20.discgolfbag.services.BagService;
import hh.swd20.discgolfbag.services.DiscService;
import hh.swd20.discgolfbag.services.UserService;

@CrossOrigin
@Controller
@RequestMapping("/users")
public class UserController {
	
	// REPOSITORIES
	@Autowired private UserRepository repository;
	
	// SERVICES
	@Autowired private BagService bagService;
	@Autowired private UserService userService;
	@Autowired private DiscService discService;
	@Autowired private BagRepository bagRepository;
	
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("signupform") SignupForm signupForm, BindingResult bindingResult, Authentication auth) {
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

				if(repository.findByUsername(newUser.getUsername()).isEmpty()) {
					
					if(repository.findByEmail(newUser.getEmail()).isEmpty()) {
						Bag bag = new Bag(newUser.getUsername() + "'s bag", "default", newUser);
						bagRepository.save(bag);
						newUser.setBag(bag);
						userService.save(newUser);
					} 
					else {
						bindingResult.rejectValue("email", "err.email", "User with this email already exists!");
						return "signup";
					}
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
		if(auth.isAuthenticated()) {
			return "redirect:/userlist";
		} else {
			return "redirect:/login";
		}
		
	}
	
	/*@RequestMapping(value = "/users/savenew", method = RequestMethod.POST)
	public String adminSaveNewUser(@Valid @ModelAttribute("signupform") SignupForm signupForm, BindingResult bindingResult) {
		
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
				
				if(repository.findByUsername(newUser.getUsername()).isEmpty()) {				
					
					if(repository.findByEmail(newUser.getEmail()).isEmpty()) {
						userService.save(newUser);
						Bag bag = new Bag(newUser.getUsername() + "'s bag", "default", newUser);
						bagRepository.save(bag);
						newUser.setBag(bag);
						userService.save(newUser);
					} 
					else {
						bindingResult.rejectValue("email", "err.email", "User with this email already exists!");
						return "adduser";
					}
				}
				else {
					bindingResult.rejectValue("username", "err.username", "Username already exists!");
					return "adduser";
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
	}*/
	
	@PostMapping("/update")
	public String saveOldUser(@ModelAttribute User user) {
		userService.save(user);
		return "redirect:/userlist";
	}
	
	@GetMapping("/userlist")
	public String listUsers(Model model, String keyword) {
		if(keyword != null) {
			model.addAttribute("users", userService.findByKeyword(keyword));
		} else {
			model.addAttribute("users", userService.getAll());
		}
		return "userlist";
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@GetMapping("/adduser")
	public String addUser(Model model) {
		model.addAttribute("signupform", new SignupForm());
		return "adduser";
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@GetMapping("/edit/{id}")
	public String editUser(@PathVariable("id") Long userId, Model model) {
		model.addAttribute("user", userService.getById(userId));
		return "edituser";
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") Long userId, Model model) {
		bagService.delete(bagService.getBagByUserId(userId));
		userService.delete(userService.getById(userId));
		return "redirect:/userlist";
	}
	
	@PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
	@RequestMapping(value = "/users/{userid}/add/{discid}", method = RequestMethod.GET)
	public String addDiscFromUser(@PathVariable("userid") Long userId, @PathVariable("discid") Long discId, Authentication auth, Model model) {
		Disc disc = discService.getById(discId);
		Long myId = userService.getByUsername(auth.getName()).getId();
		Bag bag = bagService.getBagByUserId(myId);
		if (!bag.getDiscs().contains(disc)) {
			disc.addToBag(bag);
			discService.save(disc);
			bag.addDisc(disc);
			bagService.save(bag);
		}
		model.addAttribute("userid", userId);
		
		return "redirect:/bags/{userid}";
	}
	
	@PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
	@GetMapping("/profile")
	public String userProfile(Authentication auth, Model model) {
		User user = userService.getByUsername(auth.getName());
		model.addAttribute("user", user);
		return "profile";
	}
	
}

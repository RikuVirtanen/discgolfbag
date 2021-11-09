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

import hh.swd20.discgolfbag.domain.Bag;
import hh.swd20.discgolfbag.domain.BagRepository;
import hh.swd20.discgolfbag.domain.Disc;
import hh.swd20.discgolfbag.domain.DiscRepository;
import hh.swd20.discgolfbag.domain.SignupForm;
import hh.swd20.discgolfbag.domain.User;
import hh.swd20.discgolfbag.domain.UserRepository;

@CrossOrigin
@Controller
@RequestMapping("/users")
public class UserController {
	
	// REPOSITORIES
	@Autowired private UserRepository repository;
	@Autowired private BagRepository bagRepository;
	@Autowired private DiscRepository discRepository;
	
	
	@GetMapping("")
	public String listUsers(Model model, String keyword) {
		if(keyword != null) {
			model.addAttribute("users", repository.findByKeyword(keyword));
		} else {
			model.addAttribute("users", repository.findAll());
		}
		return "userlist";
	}
	
	@PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
	@GetMapping("/bag")
	public String myBag(Model model, Authentication auth) {
		
		User user = repository.findByUsername(auth.getName());
		model.addAttribute(user);
		model.addAttribute("bag", user.getBag());
		return "bag";
	}
	
	@PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
	@GetMapping("/bag/{id}")
	public String user(@PathVariable("id") Long userId, Model model, Authentication auth, String keyword) {
	
		if(userId == repository.findByUsername(auth.getName()).getId()) {
			User user2 = repository.findByUsername(auth.getName());
			model.addAttribute("currUser", null);
			model.addAttribute("user", user2);
			model.addAttribute("bag", user2.getBag());
			return "bag";
		}
		
		model.addAttribute("currUser", repository.findByUsername(auth.getName()));
		User user3 = repository.findById(userId).get();
		model.addAttribute("user", user3);
		model.addAttribute("bag", user3.getBag());
		return "bag";
			
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
		model.addAttribute("user", repository.findById(userId).get());
		return "edituser";
	}
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@PostMapping("/update")
	public String saveOldUser(@ModelAttribute User user) {
		repository.save(user);
		return "redirect:/users";
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") Long userId, Model model) {
		if(bagRepository.findBagByUserId(userId).isPresent()) {
			bagRepository.delete(bagRepository.findBagByUserId(userId).get());
		}
		
		repository.delete(repository.findById(userId).get());
		return "redirect:/users";
	}
	
	@PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
	@GetMapping("/{userid}/add/{discid}")
	public String addDiscFromUser(@PathVariable("userid") Long userId, @PathVariable("discid") Long discId, Authentication auth, Model model) {
		Disc disc = discRepository.findById(discId).get();
		Long myId = repository.findByUsername(auth.getName()).getId();
		Bag bag = bagRepository.findBagByUserId(myId).get();
		if (!bag.getDiscs().contains(disc)) {
			disc.addToBag(bag);
			discRepository.save(disc);
			bag.addDisc(disc);
			bagRepository.save(bag);
		}
		model.addAttribute("userid", userId);
		
		return "redirect:/users/bag/{userid}";
	}
	
	@PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
	@GetMapping("/profile")
	public String userProfile(Authentication auth, Model model) {
		User user = repository.findByUsername(auth.getName());
		model.addAttribute("user", user);
		return "profile";
	}
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@PostMapping("/createuser")
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
				newUser.setRole(signupForm.getRole());

				if(repository.findByUsername(newUser.getUsername()) == null) {
					
					if(repository.findByEmail(newUser.getEmail()).isEmpty()) {
						newUser.setBag(new Bag(newUser));
						repository.save(newUser);
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
			
		return "redirect:/users";
		
	}
	
}

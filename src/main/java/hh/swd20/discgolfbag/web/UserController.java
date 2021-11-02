package hh.swd20.discgolfbag.web;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import hh.swd20.discgolfbag.domain.DGBagRepository;
import hh.swd20.discgolfbag.domain.SignupForm;
import hh.swd20.discgolfbag.domain.User;
import hh.swd20.discgolfbag.domain.UserRepository;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private DGBagRepository bRepository;
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signUser(Model model) {
		model.addAttribute("signupform", new SignupForm());
		return "signup";
	}
	
	/********************* RESTFUL SERVICES *********************************/
	
	@RequestMapping(value="/api/user/bag/{id}", method = RequestMethod.GET)
	public @ResponseBody Optional <DGBag> findDGBagByUserId(@PathVariable("id") Long userId) {
		return bRepository.findDGBagByUserId(userId);
	}
	
	@RequestMapping(value="/api/users", method = RequestMethod.GET)
	public @ResponseBody List <User> findUsersRest() {
		return (List<User>) repository.findAll();
	}
	
	@RequestMapping(value="/api/user/{id}", method = RequestMethod.GET)
	public @ResponseBody Optional <User> findUserById(@PathVariable("id") Long userId) {
		return repository.findById(userId);
	}
	
	/************************************************************************/
	
	@RequestMapping(value = "/saveuser", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("signupform") SignupForm signupForm, BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) {
			if(signupForm.getPassword().equals(signupForm.getPasswordCheck())) {
				String pwd = signupForm.getPassword();
				BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
				String hashPwd = bc.encode(pwd);
				
				User newUser = new User();
				newUser.setPasswordHash(hashPwd);
				newUser.setUsername(signupForm.getUsername());
				newUser.setEmail(signupForm.getEmail());
				newUser.setRole("USER");
				if(repository.findByUsername(signupForm.getUsername()) == null) {
					repository.save(newUser);
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
	
	@RequestMapping(value = "/savenewuser", method = RequestMethod.POST)
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
				if(repository.findByUsername(signupForm.getUsername()) == null) {
					repository.save(newUser);
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
	
	@RequestMapping(value = "/saveolduser", method = RequestMethod.POST)
	public String saveOldUser(@ModelAttribute User user) {
		repository.save(user);
		return "redirect:/users";
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String listUsers(Model model) {
		model.addAttribute("users", repository.findAll());
		return "users";
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@RequestMapping(value="/adduser", method = RequestMethod.GET)
	public String addUser(Model model) {
		model.addAttribute("signupform", new SignupForm());
		return "adduser";
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@RequestMapping(value="/edituser/{id}", method = RequestMethod.GET)
	public String editUser(@PathVariable("id") Long userId, Model model) {
		model.addAttribute("user", repository.findById(userId));
		return "edituser";
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@RequestMapping(value="/deleteuser/{id}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable("id") Long userId, Model model) {
		repository.deleteById(userId);
		return "redirect:/users";
	}
}

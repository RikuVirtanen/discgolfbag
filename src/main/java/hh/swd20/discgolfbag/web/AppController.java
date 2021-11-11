package hh.swd20.discgolfbag.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import hh.swd20.discgolfbag.domain.SignupForm;

@Controller
public class AppController {

	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
	public String main(Model model) {
		return "index";
	}
	
	@RequestMapping(value = "signup", method = RequestMethod.GET)
	public String signUser(Model model) {
		model.addAttribute("signupform", new SignupForm());
		return "signup";
	}
}

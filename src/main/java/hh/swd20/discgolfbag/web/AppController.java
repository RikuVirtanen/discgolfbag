package hh.swd20.discgolfbag.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AppController {

	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "/signup")
	public String signup() {
		return "signup";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main() {
		return "index";
	}
}

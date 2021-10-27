package hh.swd20.discgolfbag.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.swd20.discgolfbag.domain.CategoryRepository;
import hh.swd20.discgolfbag.domain.CompanyRepository;
import hh.swd20.discgolfbag.domain.Disc;
import hh.swd20.discgolfbag.domain.DiscRepository;

@CrossOrigin
@Controller
public class DiscController {
	
	@Autowired
	private DiscRepository dRepository;
	
	@Autowired 
	private CategoryRepository catRepository;
	
	@Autowired 
	private CompanyRepository comRepository;
	
	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "/storage", method = RequestMethod.GET)
	public String listDiscs(Model model) {
		model.addAttribute("discs", dRepository.findAll());
		return "storage";
	}
	
	/******************RESTFUL SERVICES **************************/
	
	@RequestMapping(value = "/discs", method = RequestMethod.GET)
	public @ResponseBody List<Disc> discList() {
		return (List<Disc>) dRepository.findAll();
	}
	
	@RequestMapping(value = "/discs/{id}", method = RequestMethod.GET)
	public @ResponseBody Optional<Disc> findDiscRest(@PathVariable("id") Long discId) {
		return dRepository.findById(discId);
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@RequestMapping(value = "/discs", method = RequestMethod.POST)
	public @ResponseBody Disc saveDiscRest(@RequestBody Disc disc) {
		return dRepository.save(disc);
	}
	
	/*************************************************************/
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveDisc(@ModelAttribute Disc disc) {
		disc.setName(capitalize(disc.getName()));
		disc.setSpeed(disc.getSpeed());
		disc.setGlide(disc.getGlide());
		disc.setTurn(disc.getTurn());
		disc.setFade(disc.getFade());
		disc.setPlastic(capitalize(disc.getPlastic()));
		disc.setInBag(disc.getInBag());
		disc.setCategory(disc.getCategory());
		disc.setCompany(disc.getCompany());
		dRepository.save(disc);
		return "redirect:/storage";
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addDisc(Model model) {
		model.addAttribute("disc", new Disc());
		model.addAttribute("categories", catRepository.findAll());
		model.addAttribute("companies", comRepository.findAll());
		return "addanotherdisc";
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editDisc(@PathVariable("id") Long discId, Model model) {
		model.addAttribute("disc", dRepository.findById(discId).get());
		model.addAttribute("categories", catRepository.findAll());
		model.addAttribute("companies", comRepository.findAll());
		return "editdisc";
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteDisc(@PathVariable("id") Long discId) {
		dRepository.deleteById(discId);
		return "redirect::/storage";
	}
	
	public String capitalize(String word) {
		String words[] = word.split("\\s");
		String outcome = "";
		for (String w: words) {
			String first = w.substring(0, 1);
			String rest = w.substring(1);
			outcome += first.toUpperCase() + rest.toLowerCase() + " ";
		}
		
		return outcome.trim();
	}
}
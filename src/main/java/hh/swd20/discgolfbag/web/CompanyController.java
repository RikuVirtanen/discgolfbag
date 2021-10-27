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

import hh.swd20.discgolfbag.domain.Company;
import hh.swd20.discgolfbag.domain.CompanyRepository;

@CrossOrigin
@Controller
public class CompanyController {
	
	@Autowired
	private CompanyRepository repository;
	
	/******************************** RESTFUL SERVICES *****************************************/
	
	@RequestMapping(value = "/companies", method = RequestMethod.GET)
	public @ResponseBody List<Company> getCompaniesRest() {
		return (List<Company>) repository.findAll();
	}
	
	@RequestMapping(value = "/companies/{id}", method=RequestMethod.GET)
	public @ResponseBody Optional<Company> findCompanyRest(@PathVariable("id") Long companyId) {
		return repository.findById(companyId);
	}
	
	@RequestMapping(value = "/companies", method = RequestMethod.POST)
	public @ResponseBody Company saveCompanyRest(@RequestBody Company company) {
		return repository.save(company);
	}
	
	/********************************************************************************************/
	
	@RequestMapping(value="/companylist", method=RequestMethod.GET)
	public String listCompanies(Model model) {
		model.addAttribute("companies", repository.findAll());
		return "companylist";
	}
	
	@RequestMapping(value="/addcompany", method=RequestMethod.GET)
	public String addCompany(Model model) {
		model.addAttribute("company", new Company());
		return "addcompany";
	}
	
	@RequestMapping(value="/savecompany", method=RequestMethod.POST)
	public String saveCompany(@ModelAttribute Company company) {
		if(!repository.findByName(capitalize(company.getName())).isEmpty()) {
			return "redirect:/companylist";
		}
		company.setName(capitalize(company.getName()));
		repository.save(company);
		return "redirect:/companylist";
	}
	@PreAuthorize(value="hasAuthority('ADMIN')")
	@RequestMapping(value="/editcompany/{id}", method=RequestMethod.GET)
	public String editCompany(@PathVariable("id") Long companyId, Model model) {
		model.addAttribute("company", repository.findById(companyId).get());
		return "/editcompany";
	}
	@PreAuthorize(value="hasAuthority('ADMIN')")
	@RequestMapping(value="/deletecompany/{id}", method=RequestMethod.GET)
	public String deleteCompany(@PathVariable("id") Long companyId) {
		
		repository.deleteById(companyId);
		return "redirect:../companylist";
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

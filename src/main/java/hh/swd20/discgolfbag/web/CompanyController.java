package hh.swd20.discgolfbag.web;

import java.util.List;

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
import hh.swd20.discgolfbag.services.CompanyService;

@CrossOrigin
@Controller
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	
	/******************************** RESTFUL SERVICES *****************************************/
	
	@RequestMapping(value = "/companies", method = RequestMethod.GET)
	public @ResponseBody List<Company> getCompaniesRest() {
		return companyService.getAll();
	}
	
	@RequestMapping(value = "/companies/{id}", method=RequestMethod.GET)
	public @ResponseBody Company findCompanyRest(@PathVariable("id") Long companyId) {
		return companyService.getById(companyId);
	}
	
	@RequestMapping(value = "/companies", method = RequestMethod.POST)
	public @ResponseBody void saveCompanyRest(@RequestBody Company company) {
		companyService.save(company);
	}
	
	/********************************************************************************************/
	
	@RequestMapping(value="/companylist", method=RequestMethod.GET)
	public String listCompanies(Model model) {
		model.addAttribute("companies", companyService.getAll());
		return "companylist";
	}
	
	@RequestMapping(value="/addcompany", method=RequestMethod.GET)
	public String addCompany(Model model) {
		model.addAttribute("company", new Company());
		return "addcompany";
	}
	
	@RequestMapping(value="/savecompany", method=RequestMethod.POST)
	public String saveCompany(@ModelAttribute Company company) {
		if(companyService.getByName(company.capitalize(company.getName())) != null) {
			return "redirect:/companylist";
		}
		company.setName(company.capitalize(company.getName()));
		companyService.save(company);
		return "redirect:/companylist";
	}
	@PreAuthorize(value="hasAuthority('ADMIN')")
	@RequestMapping(value="/editcompany/{id}", method=RequestMethod.GET)
	public String editCompany(@PathVariable("id") Long companyId, Model model) {
		model.addAttribute("company", companyService.getById(companyId));
		return "/editcompany";
	}
	@PreAuthorize(value="hasAuthority('ADMIN')")
	@RequestMapping(value="/deletecompany/{id}", method=RequestMethod.GET)
	public String deleteCompany(@PathVariable("id") Long companyId) {
		companyService.delete(companyService.getById(companyId));
		return "redirect:../companylist";
	}
}

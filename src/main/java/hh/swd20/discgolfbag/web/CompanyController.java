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
	
	@RequestMapping(value = "/api/companies", method = RequestMethod.GET)
	public @ResponseBody List<Company> getCompaniesRest() {
		return companyService.getAll();
	}
	
	@RequestMapping(value = "/api/companies/{id}", method=RequestMethod.GET)
	public @ResponseBody Company getCompanyByIdRest(@PathVariable("id") Long companyId) {
		return companyService.getById(companyId);
	}
	
	@RequestMapping(value = "/api/companies", method = RequestMethod.POST)
	public @ResponseBody void saveCompanyRest(@RequestBody Company company) {
		companyService.save(company);
	}
	
	/********************************************************************************************/
	
	@RequestMapping(value="/companies", method=RequestMethod.GET)
	public String listCompanies(Model model) {
		model.addAttribute("companies", companyService.getAll());
		return "companylist"; //thymeleaf template
	}
	
	@RequestMapping(value="/companies/addcompany", method=RequestMethod.GET)
	public String addCompany(Model model) {
		model.addAttribute("company", new Company());
		return "addcompany"; //thymeleaf template
	}
	
	@RequestMapping(value="/companies/savecompany", method=RequestMethod.POST)
	public String saveCompany(@ModelAttribute Company company) {
		if(companyService.getByName(company.capitalize(company.getName())) != null) {
			return "redirect:/companies";
		}
		company.setName(company.capitalize(company.getName()));
		companyService.save(company);
		return "redirect:/companies";
	}
	
	@PreAuthorize(value="hasAuthority('ADMIN')")
	@RequestMapping(value="/companies/editcompany/{id}", method=RequestMethod.GET)
	public String editCompany(@PathVariable("id") Long companyId, Model model) {
		model.addAttribute("company", companyService.getById(companyId));
		return "editcompany"; //thymeleaf template
	}
	
	@PreAuthorize(value="hasAuthority('ADMIN')")
	@RequestMapping(value="/companies/deletecompany/{id}", method=RequestMethod.GET)
	public String deleteCompany(@PathVariable("id") Long companyId) {
		companyService.delete(companyService.getById(companyId));
		return "redirect:../companies";
	}
}

package hh.swd20.discgolfbag.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.swd20.discgolfbag.domain.Company;
import hh.swd20.discgolfbag.domain.CompanyRepository;
import hh.swd20.discgolfbag.domain.Disc;
import hh.swd20.discgolfbag.domain.DiscRepository;
import hh.swd20.discgolfbag.domain.Plastic;
import hh.swd20.discgolfbag.domain.PlasticRepository;
import hh.swd20.discgolfbag.services.CompanyService;

@CrossOrigin
@Controller
public class CompanyController {
	
	@Autowired private CompanyService companyService;
	@Autowired private CompanyRepository repository;
	@Autowired private PlasticRepository plasticRepository;
	@Autowired private DiscRepository discRepository;
	
	/******************************** RESTFUL SERVICES *****************************************/
	
	@RequestMapping(value = "/api/companies", method = RequestMethod.GET)
	public @ResponseBody List<Company> getCompaniesRest() {
		return (List<Company>) repository.findAll();
	}
	
	@RequestMapping(value = "/api/companies/{id}", method=RequestMethod.GET)
	public @ResponseBody Company getCompanyByIdRest(@PathVariable("id") Long companyId) {
		return companyService.getById(companyId);
	}
	
	@PreAuthorize(value="hasAuthority('ADMIN')")
	@RequestMapping(value = "/api/companies", method = RequestMethod.POST)
	public @ResponseBody void saveCompanyRest(@RequestBody Company company) {
		companyService.save(company);
	}
	
	/********************************************************************************************/
	
	@RequestMapping(value="/companies", method=RequestMethod.GET)
	public String listCompanies(Model model) {
		model.addAttribute("company", new Company());
		model.addAttribute("companies", companyService.getAll());
		return "companylist"; //thymeleaf template
	}
	
	@PreAuthorize(value="hasAuthority('ADMIN')")
	@RequestMapping(value="/companies/save", method=RequestMethod.POST)
	public String saveCompany(Company company) {
		if(!repository.findByName(company.capitalize(company.getName())).isEmpty()) {
			return "redirect:/companies";
		}
		company.setName(company.capitalize(company.getName()));
		repository.save(company);
		return "redirect:/companies";
	}
	
	@RequestMapping(value="/companies/company/{id}", method = RequestMethod.GET)
	public String companyInfo(@PathVariable("id") Long companyId, Model model) {
		model.addAttribute("plastic", new Plastic());
		Company company = companyService.getById(companyId);
		model.addAttribute("plastics", company.getPlastics());
		model.addAttribute("company", company);
		return "company";
	}
	
	@PreAuthorize(value="hasAuthority('ADMIN')")
	@RequestMapping(value="/companies/editcompany/{id}", method=RequestMethod.GET)
	public String editCompany(@PathVariable("id") Long companyId, Model model) {
		model.addAttribute("company", companyService.getById(companyId));
		return "editcompany"; //thymeleaf template
	}
	
	@PreAuthorize(value="hasAuthority('ADMIN')")
	@RequestMapping(value = "/companies/company/{id}/saveplastic", method = RequestMethod.POST)
	public String savePlastic(@PathVariable("id") Long companyId, Model model, Plastic plastic) {
		Company company = companyService.getById(companyId);
		if(companyService.getById(companyId).getPlastics().contains(plastic)) {
			return "redirect:/companies/company/{id}";
		}
		else {
			plastic.setName(plastic.capitalize(plastic.getName()));
			plastic.setCompany(company);
			plasticRepository.save(plastic);
			return "redirect:/companies/company/{id}";
		}
	}
	@PreAuthorize(value="hasAuthority('ADMIN')")
	@RequestMapping(value="/companies/company/{id}/delete/{plasticid}", method = RequestMethod.GET)
	public String deletePlastic(@PathVariable("id") Long companyId, @PathVariable("plasticid") Long plasticId) {
		List<Disc> discs = plasticRepository.findById(plasticId).get().getDiscs();
		for(Disc disc : discs) {
			disc.setPlastic(null);
			discRepository.save(disc);
		}
		plasticRepository.delete(plasticRepository.findById(plasticId).get());
		return "redirect:/companies/company/{id}";
	}
	
	@PreAuthorize(value="hasAuthority('ADMIN')")
	@RequestMapping(value="/companies/delete/{id}", method=RequestMethod.GET)
	public String deleteCompany(@PathVariable("id") Long companyId) {
		Company company = companyService.getById(companyId);
		List<Disc> discs = company.getDiscs();
		for(Disc disc : discs) {
			disc.setCompany(null);
			disc.setPlastic(null);
		}
		List<Plastic> plastics = company.getPlastics();
		for(Plastic plastic : plastics) {
			plasticRepository.delete(plastic);
		}
		companyService.delete(company);
		
		return "redirect:/companies";
	}
}

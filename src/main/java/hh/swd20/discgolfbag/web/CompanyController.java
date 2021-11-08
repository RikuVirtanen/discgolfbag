package hh.swd20.discgolfbag.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	/*
	@RequestMapping(value = "/api/companies", method = RequestMethod.GET)
	public @ResponseBody List<Company> getCompaniesRest() {
		return (List<Company>) repository.findAll();
	}
	
	@RequestMapping(value = "/api/companies/{id}", method=RequestMethod.GET)
	public @ResponseBody Optional<Company> getCompanyByIdRest(@PathVariable("id") Long companyId) {
		return repository.findById(companyId);
	}
	
	@PreAuthorize(value="hasAuthority('ADMIN')")
	@RequestMapping(value = "/api/companies", method = RequestMethod.POST)
	public @ResponseBody void saveCompanyRest(@RequestBody Company company) {
		repository.save(company);
	}
	*/
	/********************************************************************************************/
	
	@RequestMapping(value="/companies")
	public String listCompanies(Model model, String keyword) {
		if(keyword != null ) {
			model.addAttribute("companies", repository.findByKeyword(keyword.toLowerCase()));
		} else {
			model.addAttribute("companies", companyService.getAll());
		}
		model.addAttribute("company", new Company());
		return "companylist"; //thymeleaf template
	}
	
	@PreAuthorize(value="hasAuthority('ADMIN')")
	@RequestMapping(value={"/companies/save", "/companies/save/{id}"}, method=RequestMethod.POST)
	public String saveCompany(@PathVariable(required=false) Long id, Company company) {
		if(id != null) {
			company.setName(company.getName().toLowerCase());
			repository.save(company);
			return "redirect:/companies";
		}
		else if(!repository.findByName(company.getName().toLowerCase()).isEmpty()) {
			return "redirect:/companies";
		}
		company.setName(company.getName().toLowerCase());
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
	@RequestMapping(value = {"/companies/company/{id}/saveplastic", "/companies/company/{id}/saveplastic/{plasticid}"}, method = RequestMethod.POST)
	public String savePlastic(@PathVariable("id") Long companyId, @PathVariable(required=false) Long plasticId, Model model, Plastic plastic) {
		Company company = companyService.getById(companyId);
		if(plasticId != null) {
			Plastic oldPlastic = plasticRepository.findById(plasticId).get();
			oldPlastic.setName(oldPlastic.getName().toLowerCase());
			plasticRepository.save(oldPlastic);
			return "redirect:/companies/company/{id}";
		}
		
		else if(companyService.getById(companyId).getPlastics().contains(plastic)) {
			return "redirect:/companies/company/{id}";
		}
		else {
			plastic.setName(plastic.getName().toLowerCase());
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

package hh.swd20.discgolfbag.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hh.swd20.discgolfbag.domain.Company;
import hh.swd20.discgolfbag.domain.CompanyRepository;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/companies")
public class RestCompanyController {

	@Autowired private CompanyRepository repository;
	
	@GetMapping("")
	public List<Company> getCompaniesRest() {
		return (List<Company>) repository.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Company> getCompanyByIdRest(@PathVariable("id") Long companyId) {
		return repository.findById(companyId);
	}
	
	@PreAuthorize(value="hasAuthority('ADMIN')")
	@PostMapping("")
	public void saveCompanyRest(@RequestBody Company company) {
		repository.save(company);
	}
	
	@PreAuthorize(value="hasAuthority('ADMIN')")
	@GetMapping("/delete/{id}")
	public void deleteCompanyRest(@PathVariable("id") Long id) {
		repository.delete(repository.findById(id).get());
	}
}

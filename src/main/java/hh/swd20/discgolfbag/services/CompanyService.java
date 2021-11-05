package hh.swd20.discgolfbag.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hh.swd20.discgolfbag.domain.Company;
import hh.swd20.discgolfbag.domain.CompanyRepository;

@Service
public class CompanyService {
	
	@Autowired private CompanyRepository repository;
	
	public List<Company> getAll() {
		return (List<Company>) repository.findAll();
	}
	
	public Company getByName(String name) {
		return repository.findByName(name).get();
	}
	
	public Company getById(Long id) {
		return repository.findById(id).get();
	}
	
	public void save(Company company) {
		repository.save(company);
	}
	
	public void delete(Company company) {
		repository.delete(company);
	}
}

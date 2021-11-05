package hh.swd20.discgolfbag.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hh.swd20.discgolfbag.domain.Company;
import hh.swd20.discgolfbag.domain.CompanyRepository;

@Service public class CompanyService {
	
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
	
	@SuppressWarnings("unchecked")
	public Map<String, Set<String>> getCompanies() {
		return (Map<String, Set<String>>) repository.findAll();
	}
	
	public void delete(Company company) {
		repository.delete(company);
	}
	
	public List<Company> getCompaniesByKeyword(String keyword) {
		keyword = capitalize(keyword);
		return (List<Company>) repository.findByKeyword(keyword);
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

package hh.swd20.discgolfbag.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hh.swd20.discgolfbag.domain.Disc;
import hh.swd20.discgolfbag.domain.DiscRepository;

@Service public class DiscService {
	
	@Autowired private DiscRepository repository;
	
	public List<Disc> getDiscs() {
		return (List<Disc>) repository.findAll();
	}
	
	public List<Disc> getDiscsByKeyword(String keyword) {
		keyword = capitalize(keyword);
		return (List<Disc>) repository.findByKeyword(keyword);
	}
	
	public List<Disc> getDiscsByPlastic(String plastic) {
		return (List<Disc>) repository.findByPlastic(plastic);
	}
	
	public List<Disc> getDiscsByCompany(String company) {
		return (List<Disc>) repository.findByCompany(company);
	}
	
	public void save(Disc disc) {
		repository.save(disc);
	}
	
	public Disc getById(Long id) {
		return repository.findById(id).get();
	}
	
	public Disc getByName(String name) {
		return repository.findByName(name).get();
	}
	
	public void delete(Disc disc) {
		repository.delete(disc);
	}
	
	public void deleteById(Long id) {
		repository.deleteById(id);
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

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

import hh.swd20.discgolfbag.domain.Bag;
import hh.swd20.discgolfbag.domain.BagRepository;
import hh.swd20.discgolfbag.domain.Disc;
import hh.swd20.discgolfbag.domain.DiscRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/bags")
public class RestBagController {
	
	@Autowired private BagRepository repository;
	@Autowired private DiscRepository discRepository;

	@GetMapping("")
	public List<Bag> getBagsRest() {
		return (List<Bag>) repository.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Bag> findBagRest(@PathVariable("id") Long bagId) {
		return repository.findById(bagId);
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@GetMapping("/delete/{id}")
	public void deleteBagRest(@PathVariable("id") Long id) {
		Bag bag = repository.findById(id).get();
		for(Disc disc : bag.getDiscs()) {
			disc.removeFromBag(bag);
			discRepository.save(disc);
		}
		repository.delete(bag);
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@PostMapping("/save")
	public void save(@RequestBody Bag bag) {
		repository.save(bag);
	}
}

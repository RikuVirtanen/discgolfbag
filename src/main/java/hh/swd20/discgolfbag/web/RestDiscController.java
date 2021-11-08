package hh.swd20.discgolfbag.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hh.swd20.discgolfbag.domain.Disc;
import hh.swd20.discgolfbag.domain.DiscRepository;

@CrossOrigin
@RestController
@RequestMapping(value = "/api", method = RequestMethod.GET)
public class RestDiscController {
	
	@Autowired private DiscRepository repository;
	
	/******************RESTFUL SERVICES **************************/
	
	@GetMapping("/discs")
	public List<Disc> discListRest() {
		return (List<Disc>) repository.findAll();
	}
	
	@GetMapping("/discs/{id}")
	public Optional<Disc> findDiscRest(@PathVariable("id") Long discId) {
		return repository.findById(discId);
	}
	
	@GetMapping("/discs/{name}")
	public Optional<Disc> findDiscRest(@PathVariable("name") String discName) {
		return repository.findByName(discName);
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@RequestMapping(value = "/api/discs", method = RequestMethod.POST)
	public @ResponseBody void saveRest(@RequestBody Disc disc) {
		repository.save(disc);
	}
}
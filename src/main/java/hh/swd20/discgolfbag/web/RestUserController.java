package hh.swd20.discgolfbag.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hh.swd20.discgolfbag.domain.User;
import hh.swd20.discgolfbag.domain.UserRepository;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/users", method = RequestMethod.GET)
public class RestUserController {
	
	@Autowired private UserRepository repository;
	
	@GetMapping("")
	public List <User> findUsersRest() {
		return (List<User>) repository.findAll();
	}

	@GetMapping("/{id}")
	public Optional<User> findUserById(@PathVariable("id") Long userId) {
		return repository.findById(userId);
	}
	
	@GetMapping("/username")
	public String currentUsername(Authentication auth) {
		return auth.getName();
	}
	
	@GetMapping("/delete/{id}")
	public void deleteUserRest(@PathVariable("id") Long id) {
		repository.delete(repository.findById(id).get());
	}
	
	@RequestMapping(value = "/api/users", method = RequestMethod.POST)
	public @ResponseBody void saveRest(@RequestBody User user) {
		repository.save(user);
	}
	
	

}

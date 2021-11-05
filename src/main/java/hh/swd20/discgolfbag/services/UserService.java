package hh.swd20.discgolfbag.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hh.swd20.discgolfbag.domain.User;
import hh.swd20.discgolfbag.domain.UserRepository;

@Service
public class UserService {
	
	@Autowired private UserRepository repository;
	
	public List<User> getAll() {
		return (List<User>) repository.findAll();
	}
	
	public List<User> findByKeyword(String keyword) {
		return repository.findByKeyword(keyword.toLowerCase());
	}
	
	public User getByUsername(String username) {
		return repository.findByUsername(username).get();
	}
	
	public Optional <User> findByUsername(String username) {
		return repository.findByUsername(username);
	}
	
	public User getById(Long id) {
		return repository.findById(id).get();
	}

	public void save(User user) {
		repository.save(user);
	}
	
	public void delete(User user) {
		repository.delete(user);
	}
	
	public User getByEmail(String email) {
		return repository.findByEmail(email).get();
	}
}

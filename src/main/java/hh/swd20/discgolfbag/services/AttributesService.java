package hh.swd20.discgolfbag.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hh.swd20.discgolfbag.domain.Attributes;
import hh.swd20.discgolfbag.domain.AttributesRepository;
import hh.swd20.discgolfbag.domain.Disc;

@Service
public class AttributesService {
	
	@Autowired private AttributesRepository repository;
	
	public Attributes getById(Long id) {
		return repository.findById(id).get();
	}
	
	public Attributes getByDisc(Disc disc) {
		return repository.findByDisc(disc).get();
	}
}

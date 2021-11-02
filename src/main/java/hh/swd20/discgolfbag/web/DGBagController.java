package hh.swd20.discgolfbag.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.swd20.discgolfbag.domain.DGBag;
import hh.swd20.discgolfbag.domain.DGBagRepository;

@CrossOrigin
@Controller
public class DGBagController {

	@Autowired
	private DGBagRepository repository;
	
	/************************* RESTFUL SERVICES ****************************/
	
	@RequestMapping(value="/api/bags", method = RequestMethod.GET)
	public @ResponseBody List<DGBag> getDGBagsRest() {
		return (List<DGBag>) repository.findAll();
	}
	
	@RequestMapping(value="/api/bag/{id}", method = RequestMethod.GET)
	public @ResponseBody Optional<DGBag> findDGBagRest(@PathVariable("id") Long bagId) {
		return repository.findById(bagId);
	}
	
	/**********************************************************************/
	
	
}

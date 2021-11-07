package hh.swd20.discgolfbag.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.swd20.discgolfbag.domain.Plastic;
import hh.swd20.discgolfbag.domain.PlasticRepository;
import hh.swd20.discgolfbag.services.PlasticService;

@CrossOrigin
@Controller
public class PlasticController {
	
	@Autowired private PlasticRepository repository;
	@Autowired private PlasticService plasticService;
	
	/************************ RESTFUL SERVICES *****************************/
	
	@RequestMapping(value = "/plastics", method = RequestMethod.GET)
	public @ResponseBody List<Plastic> getPlasticsRest() {
		return (List<Plastic>) repository.findAll();
	}
	
	@RequestMapping(value = "/api/plastics/{id}", method = RequestMethod.GET)
	public @ResponseBody Optional<Plastic> getPlasticByIdRest(@PathVariable("id") Long plasticId) {
		return repository.findById(plasticId);
	}
	
	/***********************************************************************/
	
	@PreAuthorize(value="hasAuthority('ADMIN')")
	@RequestMapping(value="/plastics/editplastic/{id}", method=RequestMethod.GET)
	public String editPlastic(@PathVariable("id") Long plasticId, Model model) {
		model.addAttribute("plastic", plasticService.getById(plasticId));
		return "editplastic";  //thymeleaf template
	}
	@PreAuthorize(value="hasAuthority('ADMIN')")
	@RequestMapping(value="/plastics/deleteplastic/{id}", method=RequestMethod.GET)
	public String deletePlastic(@PathVariable("id") Long plasticId) {
		repository.delete(plasticService.getById(plasticId));
		return "redirect:../plastics";
	}
}	

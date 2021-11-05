package hh.swd20.discgolfbag.web;

import java.util.List;

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
import hh.swd20.discgolfbag.services.PlasticService;

@CrossOrigin
@Controller
public class PlasticController {
	
	@Autowired private PlasticService plasticService;
	
	/************************ RESTFUL SERVICES *****************************/
	
	@RequestMapping(value = "/api/plastics", method = RequestMethod.GET)
	public @ResponseBody List<Plastic> getPlasticsRest() {
		return plasticService.getAll();
	}
	
	@RequestMapping(value = "/api/plastics/{id}", method = RequestMethod.GET)
	public @ResponseBody Plastic getPlasticByIdRest(@PathVariable("id") Long plasticId) {
		return plasticService.getById(plasticId);
	}
	
	/***********************************************************************/
	
	@RequestMapping(value="/plastics", method=RequestMethod.GET)
	public String listPlastics(Model model) {
		model.addAttribute("plastics", plasticService.getAll());
		return "plasticlist";  //thymeleaf template
	}
	
	@PreAuthorize(value="hasAuthority('ADMIN')")
	@RequestMapping(value="/plastics/editplastic/{id}", method=RequestMethod.GET)
	public String editPlastic(@PathVariable("id") Long plasticId, Model model) {
		model.addAttribute("plastic", plasticService.getById(plasticId));
		return "editplastic";  //thymeleaf template
	}
	@PreAuthorize(value="hasAuthority('ADMIN')")
	@RequestMapping(value="/plastics/deleteplastic/{id}", method=RequestMethod.GET)
	public String deletePlastic(@PathVariable("id") Long plasticId) {
		plasticService.delete(plasticService.getById(plasticId));
		return "redirect:../plastics";
	}
}	

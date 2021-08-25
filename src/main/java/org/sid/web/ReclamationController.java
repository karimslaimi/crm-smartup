package org.sid.web;

import javax.validation.Valid;

import org.sid.dao.ClientRepository;
import org.sid.dao.ReclamationRepository;
import org.sid.dao.TechnicienRepository;
import org.sid.entities.Client;
import org.sid.entities.Intervention;
import org.sid.entities.Reclamation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class ReclamationController {
	@Autowired
	private ReclamationRepository RRepository ;
	@Autowired
	private ClientRepository CRepo ;
	@Autowired
	private TechnicienRepository TRepository;

	
	
	
	
	
	@GetMapping("/admin/reclamation")
	public String chercher1(Model model , @RequestParam(name="page", defaultValue="0") int page ,
			@RequestParam(name="motCle", defaultValue="") String mc) {
		Page<Reclamation>pageReclamation=RRepository.findByDesignationContains("%"+mc+"%",PageRequest.of(page, 5)) ;
		model.addAttribute("listReclamation",pageReclamation.getContent()) ;
		model.addAttribute("pages",new int[pageReclamation.getTotalPages()]) ;
		model.addAttribute("currentPage",page) ; 
		model.addAttribute("motCle" , mc) ;
		return "/Reclamation/Reclamation" ;
	}
	@GetMapping("/client/reclamationC")
	public String chercher2(Model model , @RequestParam(name="page", defaultValue="0") int page,
			@RequestParam(name="motCle", defaultValue="") String mc) {
		
		
		  Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		   
	      String username = ((UserDetails)principal).getUsername();
	 		    
		Client cl=CRepo.ChercherClientusername(username);
		Page<Reclamation>pageReclamation=RRepository.findByIDClientContains(cl.getId(),PageRequest.of(page, 5)) ;
		model.addAttribute("listReclamation",pageReclamation.getContent()) ;
		model.addAttribute("pages",new int[pageReclamation.getTotalPages()]) ;
		model.addAttribute("currentPage",page) ; 
		
		return "/Reclamation/ReclamationC" ;
	}
	@GetMapping("/admin/sesreclam")
	public String reclamations(Model model , @RequestParam(name="page", defaultValue="0") int page,
			Long id) {
		
		Page<Reclamation>pageReclamation=RRepository.findByIDClientContains(id,PageRequest.of(page, 5)) ;
		model.addAttribute("listReclamation",pageReclamation.getContent()) ;
		model.addAttribute("pages",new int[pageReclamation.getTotalPages()]) ;
		model.addAttribute("currentPage",page) ; 
		
		return "/Reclamation/Reclamation" ;
	}
	 

	@GetMapping("/admin/deleter")
	public String delete1 (Long id,int page , String motCle) {
		RRepository.deleteById(id);
		return"redirect:/admin/reclamation?page="+page+"&motCle"+motCle;
	}
	
	
	@GetMapping("/client/deleter")
	public String deleteclient (Long id,int page , String motCle) {
		RRepository.deleteById(id);
		return"redirect:/client/reclamationC?page="+page+"&motCle"+motCle;
	}
	
	
	
	@PostMapping("/client/saver")
	public String save1 (Model model , @Valid Reclamation reclamation , BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {

			model.addAttribute("reclamation",reclamation);
			return"/Reclamation/FormReclamation" ;
		}
		
		    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		   
		      String username = ((UserDetails)principal).getUsername();
		 		    
			Client cl=CRepo.ChercherClientusername(username);			
		reclamation.setClient(cl);
		reclamation.setDate(new Date());
		
		RRepository.save(reclamation) ; 
		return "redirect:/client/reclamationC" ; 
	}
	
	
	

	
	@GetMapping("/admin/infor")
	public String formR (Model model , Long id) {
Reclamation reclamation=RRepository.findById(id).get();
		model.addAttribute("reclamation",reclamation) ; 

		return "infoR" ; 
	}
	
	@GetMapping("/client/FormReclamation")
	
	public String form1 (Model model) {
		
		
		model.addAttribute("reclamation",new Reclamation()) ; 
		

		
		return "/Reclamation/FormReclamation" ; 
	}

	
	
	@GetMapping("/client/editr")
	public String form1 (Model model , Long id) {
Reclamation reclamation=RRepository.findById(id).get();
		model.addAttribute("reclamation",reclamation) ; 

		return "/Reclamation/EditReclamation" ; 
	}
	
	@PostMapping("/client/savereclam")
	public String editreclam(Model model,@Valid Reclamation reclamation ,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("reclamation",reclamation);
			return "/Reclamation/EditReclamation";
		}
		Reclamation rec=RRepository.findById(reclamation.getIdR()).get();
		

		if(reclamation.getExplication()==null || reclamation.getExplication().isEmpty()) 
			reclamation.setExplication(rec.getExplication());

		if(reclamation.getClient()==null)
			reclamation.setClient(rec.getClient());
		if(reclamation.getTitre().isEmpty()){
			reclamation.setTitre(rec.getTitre());
		}

			
			
		RRepository.save(reclamation);
		
		return"redirect:/client/reclamationC";
	}
	
	@GetMapping("/admin/details")
	public String ReglerReclamation(int id,Model model) {
		Intervention inter=new Intervention();
						
		Reclamation rec=RRepository.findById((long)id).get();

	
		inter.setReclamation(rec);
		model.addAttribute("techs",TRepository.findAll()) ; 
		model.addAttribute("reclamation",rec) ; 
		model.addAttribute("intervention",inter) ; 
		return "reclamation/details";
	}

	

}

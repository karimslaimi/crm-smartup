package org.sid.web;

import javax.validation.Valid;

import org.sid.dao.ClientRepository;
import org.sid.dao.ReclamationRepository;
import org.sid.dao.TechnicienRepository;
import org.sid.entities.Client;
import org.sid.entities.Reclamation;
import org.sid.entities.technicien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
public class TechnicienController {
	@Autowired
	private TechnicienRepository TRepository ;
	
	
	
	
		@GetMapping("/admin/technicien")	
		public String chercher(Model model , @RequestParam(name="page", defaultValue="0") int page ,
				@RequestParam(name="motCle", defaultValue="") String mc) {
			Page<technicien>pageTechnicien=TRepository.findByDesignationContains("%"+mc+"%",PageRequest.of(page, 5)) ;
			model.addAttribute("listTechnicien",pageTechnicien.getContent()) ;
			model.addAttribute("pages",new int[pageTechnicien.getTotalPages()]) ;
			model.addAttribute("currentPage",page) ; 
			model.addAttribute("motCle" , mc) ;
			return "Technicien/Technicien" ;
		}

		@GetMapping("/admin/deleteT")
		public String delete (Long id,int page , String motCle) {
			TRepository.deleteById(id);
			return"redirect:/admin/technicien?page="+page+"&motCle"+motCle;
		}
		
		
		@RequestMapping(value="/admin/SaveT",method = RequestMethod.POST)
		public String save (Model model , @Valid technicien technicien , BindingResult bindingResult) {
			if(bindingResult.hasErrors()) return"FormTechnicien" ;
			TRepository.save(technicien) ; 
			return "redirect:/admin/technicien" ; 
		}
		
		@GetMapping("/admin/editT")
		public String form (Model model , Long id) {
	technicien technicien=TRepository.findById(id).get();
			model.addAttribute("technicien",technicien) ; 

			return "/technicien/EditTechnicien" ; 
		}
		
		@GetMapping("/admin/infoT")
		public String formT (Model model , Long id) {
	technicien technicien=TRepository.findById(id).get();
			model.addAttribute("technicien",technicien) ; 

			return "/technicien/infoT" ; 
		}
		
		@GetMapping("/admin/FormTechnicien")
		public String form (Model model) {
			model.addAttribute("technicien",new technicien()) ; 
			
			return "/technicien/FormTechnicien" ; 
		}
		
		
		@GetMapping("/Technicien/EditProfile")
		public String editProfile(Model model,String flag) {
			technicien cl =new technicien();

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			  
		      String username =  auth.getName();
		 		    
			 cl=TRepository.ChercherTechnicienusername(username);
			 
			 cl.setPassword("");
			 model.addAttribute("technicien",cl) ;
			 if(flag!=null && flag.compareTo("done")==0) {
				 model.addAttribute("msg","Votre compte a été modifier");
				 model.addAttribute("falg","done");
			 }
			 else if (flag!=null && flag.compareTo("error")==0)
			 {
				 model.addAttribute("msg","une erreur est survenu réessayez");
				 model.addAttribute("flag","error");
			 }
			 return "Technicien/EditProfile";
			
		}
		
		@RequestMapping(value="/Technicien/saveProfile",method=RequestMethod.POST)
		public String editProfile(Model model,@Valid technicien tech , BindingResult bindingResult,RedirectAttributes redirectAttrs) {
			
			
		
			redirectAttrs.addAttribute("flag", "done");
			
			if(bindingResult.hasErrors()) {
				redirectAttrs.addAttribute("flag", "error");
				return"redirect:/Technicien/EditProfile" ;
			}
			
			
			PasswordEncoder bcpe=new BCryptPasswordEncoder() ;
				
			tech.setActive(true);
			tech.setRole("TECHNICIEN");
			technicien cl=TRepository.findById(tech.getId()).get();
			
			if(tech.getDateN()==null)
				tech.setDateN(cl.getDateN());
			
			if(tech.getAdresse()==null)
				tech.setAdresse(cl.getAdresse());
			if(tech.getCin()==null)
				tech.setCin(cl.getCin());
			if(tech.getMail()==null)
				tech.setMail(cl.getMail());
			if(tech.getMobile()==0)
				tech.setMobile(cl.getMobile());
			if(tech.getNom()==null)
				tech.setNom(cl.getNom());
			if(tech.getPrenom()==null)
				tech.setPrenom(cl.getPrenom());
			

			
			if(tech.getPassword()!=null && !tech.getPassword().isEmpty() ) {
				tech.setPassword(bcpe.encode(tech.getPassword()));
			}else {
				tech.setPassword(TRepository.ChercherTechnicienusername(tech.getUsername()).getPassword());
			}
			TRepository.save(tech);
			
			
			
			return "redirect:/Technicien/EditProfile";
		}

		
	}


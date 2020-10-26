package org.sid.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.sid.dao.ClientRepository;
import org.sid.dao.ReclamationRepository;
import org.sid.entities.Client;
import org.sid.entities.Reclamation;
import org.sid.entities.users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
public class ClientController {
	@Autowired
	private ClientRepository CRepository ;
	
	
	
	
		@GetMapping("/admin/clients")	
		public String chercher(Model model , @RequestParam(name="page", defaultValue="0") int page ,
				@RequestParam(name="motCle", defaultValue="") String mc) {
			Page<Client>pageClient=CRepository.findByDesignationContains("%"+mc+"%",PageRequest.of(page, 5)) ;
			model.addAttribute("listClient",pageClient.getContent()) ;
			model.addAttribute("pages",new int[pageClient.getTotalPages()]) ;
			model.addAttribute("currentPage",page) ; 
			model.addAttribute("motCle" , mc) ;
			return "Client/Client" ;
		}

		@GetMapping("/admin/delete")
		public String delete (Long id,int page , String motCle) {
			CRepository.deleteById(id);
			return"redirect:/admin/clients?page="+page+"&motCle"+motCle;
		}
		
		
		@RequestMapping(value="/admin/SaveC",method = RequestMethod.POST)
		public String save (Model model , @Valid Client client , BindingResult bindingResult) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			//client.setDateN(sdf..format(client.getDateN()));
			System.out.println(client.getDateN());
			if(bindingResult.hasErrors()) return"/Client/FormClient" ;
			
			client.setActive(true);
			client.setPassword(new BCryptPasswordEncoder().encode(client.getPassword()));
			client.setRole("CLIENT");
			
			CRepository.save(client) ; 
			return "redirect:/admin/clients" ; 
		}
		
		@GetMapping("/admin/edit")
		public String form (Model model , Long id) {
	Client client=CRepository.findById(id).get();
			model.addAttribute("client",client) ; 

			return "/Client/EditClient" ; 
		}
		
		@GetMapping("/admin/infop")
		public String formP (Model model , Long id) {
	Client client=CRepository.findById(id).get();
			model.addAttribute("client",client) ; 

			return "/Client/infoP" ; 
		}
		
		@GetMapping("/admin/FormClient")
		public String form (Model model) {
			model.addAttribute("client",new Client()) ; 
			
			return "/Client/FormClient" ; 
		}
		

		@GetMapping("/")
		public String def() {

			return "redirect:/user/index" ; 
		}
	
		
		
		@GetMapping("/Client/EditProfile")
		public String editProfile(Model model,String flag) {
			Client cl =new Client();

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			  
		      String username =  auth.getName();
		 		    
			 cl=CRepository.ChercherClientusername(username);
			 
			 cl.setPassword("");
			 model.addAttribute("client",cl) ;
			 if(flag!=null && flag.compareTo("done")==0) {
				 model.addAttribute("msg","Votre compte a été modifier");
				 model.addAttribute("falg","done");
			 }
			 else if (flag!=null && flag.compareTo("error")==0)
			 {
				 model.addAttribute("msg","une erreur est survenu réessayez");
				 model.addAttribute("flag","error");
			 }
			 return "Client/editProfile";
			
		}
		
		
		@RequestMapping(value="/Client/saveProfile",method=RequestMethod.POST)
		public String editProfile(Model model,@Valid Client client , BindingResult bindingResult,RedirectAttributes redirectAttrs) {
			
			
		
			redirectAttrs.addAttribute("flag", "done");
			
			if(bindingResult.hasErrors()) {
				redirectAttrs.addAttribute("flag", "error");
				return"redirect:/Client/EditProfile" ;
			}
			
			
			PasswordEncoder bcpe=new BCryptPasswordEncoder() ;
				
			client.setActive(true);
			client.setRole("CLIENT");
			Client cl=CRepository.findById(client.getId()).get();
			
			if(client.getDateN()==null)
			client.setDateN(cl.getDateN());
			
			if(client.getAdresse()==null)
				client.setAdresse(cl.getAdresse());
			if(client.getCin()==null)
				client.setCin(cl.getCin());
			if(client.getMail()==null)
				client.setMail(cl.getMail());
			if(client.getMobile()==0)
				client.setMobile(cl.getMobile());
			if(client.getNom()==null)
				client.setNom(cl.getNom());
			if(client.getPrenom()==null)
				client.setPrenom(cl.getPrenom());
			

			
			if(client.getPassword()!=null && !client.getPassword().isEmpty() ) {
				client.setPassword(bcpe.encode(client.getPassword()));
			}else {
				client.setPassword(CRepository.ChercherClientusername(client.getUsername()).getPassword());
			}
			CRepository.save(client);
			
			
			
			return "redirect:/Client/EditProfile";
		}
	
		
		
	}


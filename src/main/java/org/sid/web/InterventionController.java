package org.sid.web;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.sid.dao.InterventionRepository;
import org.sid.dao.ReclamationRepository;
import org.sid.dao.TechnicienRepository;
import org.sid.entities.Client;
import org.sid.entities.Intervention;
import org.sid.entities.Reclamation;
import org.sid.entities.technicien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Controller
public class InterventionController {
	@Autowired
	private InterventionRepository IRepository ;
	@Autowired
	private TechnicienRepository TRepository ;
	@Autowired
	private ReclamationRepository RRepository;

	@GetMapping("/admin/intervention")
	public String chercher1(Model model , @RequestParam(name="page", defaultValue="0") int page ,
			@RequestParam(name="motCle", defaultValue="") String mc) {
		Page<Intervention>pageIntervention=IRepository.findByDesignationContains("%"+mc+"%",PageRequest.of(page, 5)) ;
		model.addAttribute("listIntervention",pageIntervention.getContent()) ;
		model.addAttribute("pages",new int[pageIntervention.getTotalPages()]) ;
		model.addAttribute("currentPage",page) ; 
		model.addAttribute("motCle" , mc) ;
		return "/Intervention/Intervention" ;
	}
	
	@GetMapping("/admin/mapinterventions")
	public String getInterventionsMap(Model model)
	{
		
		return "/Intervention/Map";
	}
	
	
	
	@GetMapping("/technicien/interventionT")
	public String chercher2(Model model , @RequestParam(name="page", defaultValue="0") int page ,
			@RequestParam(name="motCle", defaultValue="") String mc) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		   
	      String username = ((UserDetails)principal).getUsername();
	 		    
		technicien tech=TRepository.ChercherTechnicienusername(username);
		Page<Intervention>pageIntervention=IRepository.findByIDtechnicienContains(tech.getId(),PageRequest.of(page, 5)) ;
		model.addAttribute("listIntervention",pageIntervention.getContent()) ;
		model.addAttribute("pages",new int[pageIntervention.getTotalPages()]) ;
		model.addAttribute("currentPage",page) ; 
		
		return "/Intervention/InterventionT" ;
	}

	@GetMapping("/admin/deletei")
	public String delete1 (Long id,int page , String motCle) {
		IRepository.deleteById(id);
		return"redirect:/admin/intervention?page="+page+"&motCle"+motCle;
	}
	@GetMapping("/technicien/deletei")
	public String deleteTech (Long id,int page , String motCle) {
		IRepository.deleteById(id);
		return"redirect:/technicien/intervention?page="+page+"&motCle"+motCle;
	}
	
	
	@PostMapping("/admin/savei")
	public String save1 (Model model , @Valid Intervention intervention ,Long id, BindingResult bindingResult) {
		Reclamation rec=RRepository.findById((long)id).get();
		intervention.setLocalisation(rec.getAddresse()+" "+rec.getCodeP());
		intervention.setReclamation(rec);
		if(bindingResult.hasErrors()|| intervention==null) return"/Intervention/FormIntervention" ;
		
		IRepository.save(intervention) ;
		
		
		sendmail(intervention,rec);
		
		
		
		return "redirect:/admin/intervention" ; 
	}
	
	@GetMapping("/admin/editi")
	public String form1 (Model model , long idInt) {
		Intervention intervention=IRepository.findById((long)idInt).get();
		model.addAttribute("techs",TRepository.findAll()) ; 
		model.addAttribute("intervention",intervention) ; 

		return "/Intervention/EditIntervention" ; 
	}
	
	@PostMapping("/admin/modififinter")
	public String modifinter (Model model , @Valid Intervention intervention,BindingResult bindingResult,long reclamation) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("intervention",intervention) ; 

			return "/Intervention/EditIntervention" ; 
		}
		Reclamation rec=RRepository.findById(reclamation).get();
		intervention.setReclamation(rec);
		IRepository.save(intervention) ;

		sendmail(intervention,intervention.getReclamation());

		return "redirect:/admin/intervention"; 
	}
	
	
	
	@GetMapping("/admin/infoi")
	public String formR (Model model , Long id) {
Intervention intervention=IRepository.findById(id).get();
		model.addAttribute("intervention",intervention) ; 

		return "infoI" ; 
	}
	
	@GetMapping("/admin/FormIntervention")
	
	public String form1 (Model model) {
		
		
		model.addAttribute("intervention",new Intervention()) ; 
		model.addAttribute("techs",TRepository.findAll()) ; 
		
		
		
		
		

		return "/Intervention/FormIntervention" ; 
	}
	
	
	
	@GetMapping("/admin/sesintervention")
	public String sesinterventions(Model model , @RequestParam(name="page", defaultValue="0") int page ,
		Long id) {
		Page<Intervention>pageIntervention=IRepository.findByIDtechnicienContains(id,PageRequest.of(page, 5));
		model.addAttribute("listIntervention",pageIntervention.getContent()) ;
		model.addAttribute("pages",new int[pageIntervention.getTotalPages()]) ;
		model.addAttribute("currentPage",page) ; 
		
		return "/Intervention/Intervention" ;
	}

	
	public void sendmail(Intervention intervention , Reclamation rec) {

		Properties props = new Properties();
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.port", "587");
		   
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			   @Override
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication("ghassen.salhi@esen.tn", "krjfyjayfugyheax");
		      }
		   });
		   try {

	            Message message = new MimeMessage(session);
	           
	            message.setRecipient(Message.RecipientType.TO,new InternetAddress(rec.getClient().getMail()));
	            message.setSubject("Réclamation traité");
	            message.setText("Votre réclamation à propos "+rec.getTypeR()+"  une intervention est planifié poru le "+
	    	            intervention.getDateInt()+" \n le technicien "+intervention.getTechnicien().getNom() +" "+
	    	            intervention.getTechnicien().getPrenom()+" qui va fixer votre probléme.\nCordialement.");

	            Transport.send(message);

	        

	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
		
	}

	

}

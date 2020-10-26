package org.sid.web;

import java.net.URL;
import java.util.Random;
import java.net.*;
import java.util.Base64;
import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.sid.dao.AdminRepository;
import org.sid.dao.ClientRepository;
import org.sid.entities.Admin;
import org.sid.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twilio.Twilio;

@Controller
public class UserController {
	@Autowired
	private ClientRepository CRepository ;
	
	
	 public static final String ACCOUNT_SID =
	            "AC5a9fa09cc49450f2f7cecef4eeb01007";
	    public static final String AUTH_TOKEN =
	            "e576de64b5951acfc47b89e1e7ac5dc6";

	    public String sendsms(String content,String number) throws IOException {
	    	String myURI = "https://api.bulksms.com/v1/messages";
	    	 String myUsername = "ghassen";
	    	    String myPassword = "Ghassen2020";
	    	    // the details of the message we want to send
	    	    String myData = "{to: \"+216"+number+"\", encoding: \"UNICODE\", body: \""+content+"\"}";
	    	
	    	 // build the request based on the supplied settings
	    	    URL url = new URL(myURI);
	    	    HttpURLConnection request = (HttpURLConnection) url.openConnection();
	    	    request.setDoOutput(true);

	    	    // supply the credentials
	    	    String authStr = myUsername + ":" + myPassword;
	    	    String authEncoded = Base64.getEncoder().encodeToString(authStr.getBytes());
	    	    request.setRequestProperty("Authorization", "Basic " + authEncoded);

	    	    // we want to use HTTP POST
	    	    request.setRequestMethod("POST");
	    	    request.setRequestProperty( "Content-Type", "application/json");

	    	    // write the data to the request
	    	    OutputStreamWriter out = new OutputStreamWriter(request.getOutputStream());
	    	    out.write(myData);
	    	    out.close();

	    	    // try ... catch to handle errors nicely
	    	    try {
	    	      // make the call to the API
	    	      InputStream response = request.getInputStream();
	    	      BufferedReader in = new BufferedReader(new InputStreamReader(response));
	    	      String replyText;
	    	      while ((replyText = in.readLine()) != null) {
	    	        System.out.println(replyText);
	    	      }
	    	      in.close();
	    	    } catch (IOException ex) {
	    	      System.out.println("An error occurred:" + ex.getMessage());
	    	      BufferedReader in = new BufferedReader(new InputStreamReader(request.getErrorStream()));
	    	      // print the detail that comes with the error
	    	      String replyText;
	    	      while ((replyText = in.readLine()) != null) {
	    	        System.out.println(replyText);
	    	      }
	    	      in.close();
	    	    }
	    	    request.disconnect();
	    	  
	    	
	        

	      return"";
	    }
	    
	    
	
	@GetMapping("/403")
	public String notAutorized() {

		return "403" ; 
	}
	@GetMapping("/login")
	public String login() {

		return "login" ; 
	}
	@GetMapping("/admin/dashboard")
	public String dashboard() {
		

		return "/Stats/dashboard" ; 
	}
	
	@RequestMapping(value="/logout")
	public String logout() {
		return "redirect:/login";
	}
	
	
	@RequestMapping(value="/forgotpass")
	public String forgotpassword() {
		//when the user click forgot password he will be redirected to this page
		
		
		return "/User/forgotpassword";

	}
	@RequestMapping(value="/requestpass")
	public String requestpasswordchange(String mail,Model model,HttpSession session) throws IOException {
		//he will give his mail and and an sms will be sent to his phone and will be redirected to enter the sms
		
		Client c=CRepository.findbymail(mail);
		if(c==null) {
			model.addAttribute("msg", "email non trouvé");
			return "/User/forgotpassword";
		}
		//send the sms
		 String code = "1234567890";
	        StringBuilder salt = new StringBuilder();
	        Random rnd = new Random();
	        while (salt.length() < 6) { // length of the random string.
	            int index = (int) (rnd.nextFloat() * code.length());
	            salt.append(code.charAt(index));
	        }
	        String codesms = salt.toString();
		
		
		session.setAttribute("code", codesms); 
		model.addAttribute("userid", c.getId());
		//sendsms(codesms,String.valueOf(c.getMobile()));
		
		
		return "/User/verifycode";
	}

	
	
	@RequestMapping(value="/verifycode")

	public String verifyCode(HttpServletRequest request,Model model,String code,int id) {
		//if the code is ok he will be redirected to a page to change his password otherwise he will stay in the same page with error msg
		model.addAttribute("userid", id);
		String cod=request.getSession().getAttribute("code").toString();
		if(cod.compareTo(code)==0) {
			
			return "/User/newpassword"; 
			
		}else {
			model.addAttribute("message", "code non valide");
			return "/User/verifycode";
		}
		
		
		
	}
	
	
	@RequestMapping(value="/newpass")
	public String newpassword(Model model,int id,String password,String confpassword) {
		//he will type the password and the password will be submitted and he will be redirected to login
		if(id==0) {
			return "redirect:/login";
			
		}
		
		if(password.compareTo(confpassword)!=0 || password.isEmpty()|| password==null) {
			model.addAttribute("mdp", "les mots de passe ne correspondent pas");
			model.addAttribute("userid", id);
			return "/User/newpassword"; 
			
		}
		PasswordEncoder bcpe=new BCryptPasswordEncoder();
		Client c=CRepository.findById((long)id).get();
		c.setPassword(bcpe.encode(password));
		CRepository.save(c);
		
		return "redirect:/login";
	}
	
	
	@Autowired
	AdminRepository adminrep;
	
	@RequestMapping(value="/Admin/EditProfile")
	public String Admineditprofile(Model model ,String flag) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Admin adm=adminrep.ChercherAdminusername(auth.getName());
		adm.setPassword("");
		 model.addAttribute("admin",adm) ;
		 if(flag!=null && flag.compareTo("done")==0) {
			 model.addAttribute("msg","Votre compte a été modifier");
			 model.addAttribute("falg","done");
		 }
		 else if (flag!=null && flag.compareTo("error")==0)
		 {
			 model.addAttribute("msg","une erreur est survenu réessayez");
			 model.addAttribute("flag","error");
		 }
		
		
		return "/User/Editprofilead";
	}
	
	
	@RequestMapping(value="/Admin/saveProfile",method=RequestMethod.POST)
	public String editProfile(Model model,@Valid Admin admin , BindingResult bindingResult,RedirectAttributes redirectAttrs) {
		
		
	
		redirectAttrs.addAttribute("flag", "done");
		
		if(bindingResult.hasErrors()) {
			redirectAttrs.addAttribute("flag", "error");
			return"redirect:/Admin/EditProfile" ;
		}
		
		
		PasswordEncoder bcpe=new BCryptPasswordEncoder() ;
			
		admin.setActive(true);
		admin.setRole("ADMIN");
		Admin ad=adminrep.findById(admin.getId()).get();
		
		
		if(admin.getNom()==null)
			admin.setNom(ad.getNom());
		if(admin.getPrenom()==null)
			admin.setPrenom(ad.getPrenom());
		

		
		if(admin.getPassword()!=null && !admin.getPassword().isEmpty() ) {
			admin.setPassword(bcpe.encode(admin.getPassword()));
		}else {
			admin.setPassword(CRepository.ChercherClientusername(admin.getUsername()).getPassword());
		}
		adminrep.save(admin);
		
		
		
		return "redirect:/Admin/EditProfile";
	}

	
	
	
	
}

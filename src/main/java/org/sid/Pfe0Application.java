package org.sid;

import java.util.Date;

import org.sid.dao.AdminRepository;
import org.sid.dao.ClientRepository;
import org.sid.dao.ReclamationRepository;
import org.sid.dao.TechnicienRepository;
import org.sid.dao.UsersRepository;
import org.sid.entities.Admin;
import org.sid.entities.Client;
import org.sid.entities.technicien;
import org.sid.entities.users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class Pfe0Application implements CommandLineRunner {
@Autowired

private ClientRepository  cr ;
@Autowired
private ReclamationRepository RRepository ;
@Autowired
private AdminRepository ur ;
@Autowired
private  TechnicienRepository tr ;


	public static void main(String[] args) {
		SpringApplication.run(Pfe0Application.class, args);
	}

	
	@Override
	public void run(String... args) throws Exception {
		PasswordEncoder bcpe=passwordenc() ;
 
	//ur.save(new Admin("admin",bcpe.encode("12345"),true,"mohamed","stamboli","ADMIN")) ;
	
		/*tr.save(new technicien("daoud", "ka7la", 111115, 
				new Date(), "chicha", "ahram",
			1478523, "daoud69.ahram@gmail.com",
			"daoudachicha95", bcpe.encode("chicha")));*/
		
	/*cr.save(new Client("homme","ghassen","salhi",
			123456,new Date(),"aaaaa",1452,"mohamed.stambouli@esen.tn",
			bcpe.encode("12345"),"mohamed")) ;*/
		
	}
	@Bean
	public PasswordEncoder passwordenc() {
	    return new BCryptPasswordEncoder();
	}
	

}

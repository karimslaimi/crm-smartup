package org.sid;

import org.sid.dao.AdminRepository;
import org.sid.dao.ClientRepository;
import org.sid.dao.ReclamationRepository;
import org.sid.dao.TechnicienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class StageApplication {
	@Autowired

	private ClientRepository  cr ;
	@Autowired
	private ReclamationRepository RRepository ;
	@Autowired
	private AdminRepository ur ;
	@Autowired
	private  TechnicienRepository tr ;


	public static void main(String[] args) {
		SpringApplication.run(StageApplication.class, args);
	}

	/*@Bean
	public CommandLineRunner init() {
		return (args -> {
			Admin admin=new Admin();
			PasswordEncoder bcpe=new BCryptPasswordEncoder();
			admin.setNom("admin");
			admin.setActive(true);
			admin.setPrenom("admin");
			admin.setUsername("admin");
			admin.setRole("ADMIN");
			String pass=bcpe.encode("admin123");
			admin.setPassword(pass);
			ur.save(admin);
		});
	}*/

	@Bean
	public PasswordEncoder passwordenc() {
		return new BCryptPasswordEncoder();
	}


}

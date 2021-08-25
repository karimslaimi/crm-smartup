package org.sid.entities;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Entity

@DiscriminatorValue("A")
public class Admin extends users implements Serializable {


	
	private String nom ; 
	private String prenom ;
	
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public Admin(String username, String password, boolean active,  String nom, String prenom,String role) {
		super(username, password, active,role);
		
		this.nom = nom;
		this.prenom = prenom;
	}
	public Admin() {
		super();
	}
	
	
	
}

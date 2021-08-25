package org.sid.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="techniciens")
@DiscriminatorValue("T")
public class technicien extends users implements Serializable {

	@NotBlank( message = "Ne doit pas etre vide")
	private String nom ;

	@NotBlank( message = "Ne doit pas etre vide")
	private String prenom ;

	@NotBlank( message = "Ne doit pas etre vide")
	@Size (min=8, max=8)
	private String cin ;





	@Enumerated(EnumType.STRING)
	private TypeTech spec ;
	
	@NotBlank( message = "Ne doit pas etre vide")
	@Size (min=5, max=70)
	private String adresse ;
	
	@NotBlank( message = "Ne doit pas etre vide")
	@Length(min = 8, max = 8,message="taille incorrect doit etre 8")
	@Pattern(regexp = "([0-9]*$)",message="numéro invalide")
	@Column(name="mobile",length=8,unique=true)
	private String mobile ; 
	
	@NotBlank( message = "Ne doit pas etre vide")	
	@Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}",message="Format mail invalide")
	private String mail ;

	@OneToMany(mappedBy="technicien",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Intervention> inter; 
	
	
	public technicien( String nom, String prenom, 
			String cin, TypeTech spec,
			String adresse,
			String mobile, String mail,String username, String password) {
		super(username,password,true,"TECHNICIEN");
		
		this.nom = nom;
		this.prenom = prenom;
		this.cin = cin;

		this.spec = spec;
		this.adresse = adresse;
		this.mobile = mobile;
		this.mail = mail;
	}
	public technicien() {
		super();
		// TODO Auto-generated constructor stub
	}
	
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
	public String getCin() {
		return cin;
	}
	public void setCin(String cin) {
		this.cin = cin;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public List<Intervention> getInter() {
		return inter;
	}
	public void setInter(List<Intervention> inter) {
		this.inter = inter;
	}

	public TypeTech getSpec() {
		return spec;
	}

	public void setSpec(TypeTech spec) {
		this.spec = spec;
	}
}

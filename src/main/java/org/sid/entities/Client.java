package org.sid.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="Client")
@DiscriminatorValue("C")
public class Client extends users implements Serializable {

	private String sexe  ;
	
	@NotBlank(message = "Ne doit pas etre vide")
	private String nom ; 
	
	@NotBlank(message = "Ne doit pas etre vide")	
	private String prenom ;

	@NotBlank( message = "Ne doit pas etre vide")
	@Length(min = 8, max = 8,message="taille incorrect doit etre 8")
	@Pattern(regexp = "([0-9]*$)")
	@Column(name="cin",length=8,unique=true)
	private String cin ;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="date_naissance")
	private Date dateN ;

	@NotBlank(message="Ne doit pas etre null")
	@Size (min=5, max=70,message="taille incorrect doit etre supérieur à 5")
	private String adresse ;

	@NotBlank( message = "Ne doit pas etre vide")
	@Length(min = 8, max = 8,message="taille incorrect doit etre 8")
	@Pattern(regexp = "([0-9]*$)",message="numéro invalide")
	@Column(name="mobile",length=8,unique=true)
	private String mobile ;

	@NotBlank( message = "Ne doit pas etre vide")
	@Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}",message="Format mail invalide")
	private String mail ;
	
	
	@OneToMany(mappedBy="client" , fetch=FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Reclamation> rec; 

	
	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public Client(String sexe, String nom, String prenom, 
			String i, Date dateN, 
			String adresse, String mobile, String mail,
			String password,String username
			) {
		super(username,password,true,"CLIENT");
		this.sexe = sexe;
		this.nom = nom;
		this.prenom = prenom;
		this.cin =i;
		this.dateN = dateN;
		this.adresse = adresse;
		this.mobile = mobile;
		this.mail = mail;
		
	}
	public Client(String sexe, String nom, String prenom, 
			String i, Date dateN, 
			String adresse, String mobile, String mail,String username
			) {
		super(username,true,"CLIENT");
		this.sexe = sexe;
		this.nom = nom;
		this.prenom = prenom;
		this.cin =i;
		this.dateN = dateN;
		this.adresse = adresse;
		this.mobile = mobile;
		this.mail = mail;
	}

	

	public List<Reclamation> getRec() {
		return rec;
	}





	public void setRec(List<Reclamation> rec) {
		this.rec = rec;
	}





	public Date getDateN() {
		return dateN;
	}


	public void setDateN(Date dateN) {
		this.dateN = dateN;
	}


	
	public String getSexe() {
		return sexe;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
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








	  
	
	
	
	

}

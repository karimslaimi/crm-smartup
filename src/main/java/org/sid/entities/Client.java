package org.sid.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import javax.validation.constraints.NotNull;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SpringBootApplication
@Entity
@Table(name="Client")
@DiscriminatorValue("C")
public class Client extends users implements Serializable {

	private String sexe  ;
	private String nom ; 
	private String prenom ;
	private Long cin ;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="date_naissance")
	private Date dateN ;
	private String adresse ; 
	private int mobile ; 
	private String mail ;
	
	
	@OneToMany(mappedBy="client" , fetch=FetchType.EAGER)
	@JsonIgnore
	private List<Reclamation> rec; 

	
	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public Client(String sexe, String nom, String prenom, 
			int i, Date dateN, 
			String adresse, int mobile, String mail,
			String password,String username
			) {
		super(username,password,true,"CLIENT");
		this.sexe = sexe;
		this.nom = nom;
		this.prenom = prenom;
		this.cin =(long)i;
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
	public Long getCin() {
		return cin;
	}
	public void setCin(Long cin) {
		this.cin = cin;
	}
	
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public int getMobile() {
		return mobile;
	}
	public void setMobile(int mobile) {
		this.mobile = mobile;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}








	  
	
	
	
	

}

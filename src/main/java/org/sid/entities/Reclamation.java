package org.sid.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@SpringBootApplication
@Entity
@Table(name = "Reclamation")
public class Reclamation implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idR;
	
	private String Fixe;//chnya hetha
	private String addresse;
	private int codeP;
	private String typeR;
	private String explication;
	@ManyToOne
	@JoinColumn(name = "client_id")
	@JsonIgnore
	private Client client;
	
	@ManyToOne
	@JoinColumn(name = "produit_id",referencedColumnName = "id")
	private Produit produit;

	
	
	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public Reclamation(Long idR, String fixe, String addresse, int codeP, String typeR,
			String explication, Client client) {
		super();
		this.idR=idR;
		this.Fixe = fixe;
		this.addresse = addresse;
		this.codeP = codeP;
		this.typeR = typeR;
		this.explication = explication;
		this.client = client;
	}
	public Reclamation( String fixe, String addresse, int codeP, String typeR,
			String explication ) {
		super();
		
		this.Fixe = fixe;
		this.addresse = addresse;
		this.codeP = codeP;
		this.typeR = typeR;
		this.explication = explication; 
	}

	public Reclamation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getIdR() {
		return idR;
	}

	public void setIdR(Long idR) {
		this.idR = idR;
	}



	public String getFixe() {
		return Fixe;
	}

	public void setFixe(String fixe) {
		Fixe = fixe;
	}

	public String getAddresse() {
		return addresse;
	}

	public void setAddresse(String addresse) {
		this.addresse = addresse;
	}

	public int getCodeP() {
		return codeP;
	}

	public void setCodeP(int codeP) {
		this.codeP = codeP;
	}

	public String getTypeR() {
		return typeR;
	}

	public void setTypeR(String typeR) {
		this.typeR = typeR;
	}

	public String getExplication() {
		return explication;
	}

	public void setExplication(String explication) {
		this.explication = explication;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	
}

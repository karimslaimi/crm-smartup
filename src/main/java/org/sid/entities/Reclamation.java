package org.sid.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "Reclamation")
public class Reclamation implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idR;

	



	@NotBlank(message="Ne doit pas etre vide")
	@Size (min=5, max=70,message="taille incorrect doit etre supérieur à 5")
	private String titre;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;


	@NotBlank(message="Ne doit pas etre vide")
	private String explication;


	private String type;
	private String categorie;
	private String priorite;
	private String problem;

	@ManyToOne
	@JoinColumn(name = "client_id")
	@JsonIgnore
	private Client client;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "intervention_id", referencedColumnName = "idInt")
	private Intervention intervention;


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public String getPriorite() {
		return priorite;
	}

	public void setPriorite(String priorite) {
		this.priorite = priorite;
	}

	public Intervention getIntervention() {
		return intervention;
	}

	public void setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

	public Reclamation(Long idR, String titre, String explication, Client client, Date date) {
		super();
		this.idR=idR;
		this.titre = titre;
		this.date=date;
		this.explication = explication;
		this.client = client;
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


	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

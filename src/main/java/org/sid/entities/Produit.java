package org.sid.entities;


import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
@Entity
public class Produit implements Serializable {
	
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	private long id;
	@NotNull
	@Size (min=5, max=70)
	private String designation ;
	
	//@JsonIgnore
	@OneToMany(mappedBy="produit")
	@JsonBackReference
	private List<Reclamation> reclamations;
	
	
	
	
	public List<Reclamation> getReclamations() {
		return reclamations;
	}
	public void setReclamations(List<Reclamation> reclamations) {
		this.reclamations = reclamations;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Produit() {
		
	}
	public Produit( String designation) {
		
		this.designation = designation;
		
	}

	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	

}

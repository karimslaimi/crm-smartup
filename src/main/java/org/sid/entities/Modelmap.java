package org.sid.entities;

public class Modelmap {
	
	private Long id;
	private String address ;
	private String nom;
	private String prenom;
	private String date;
	private String lat;
	private	 String lon;
	
	
	
	
	
	
	public Modelmap(Long id, String address, String nom, String prenom, String date) {
		super();
		this.id = id;
		this.address = address;
		this.nom = nom;
		this.prenom = prenom;
		this.date = date;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	 
	 

}

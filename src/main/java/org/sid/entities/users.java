package org.sid.entities;

import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@DiscriminatorColumn(name = "US_Type")
@Table(name="users")
public class users implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Long  id ;
private String username ;
private String password ;
private boolean active ;
private String role;

public users(String username, String password, boolean active,String role) {
	super();
	this.username = username;
	this.password = password;
	this.active = active;
	this.role=role;
}
public users() {
	super();
	// TODO Auto-generated constructor stub
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public boolean isActive() {
	return active;
}
public void setActive(boolean active) {
	this.active = active;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}


	
}

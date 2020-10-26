package org.sid.dao;

import org.sid.entities.Admin;
import org.sid.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminRepository extends JpaRepository<Admin,Long> {
	
	@Query("select c from Admin c where (c.username like:x)")
	public Admin ChercherAdminusername(@Param("x")String username);

}

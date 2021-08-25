package org.sid.dao;

import java.util.List;

import org.sid.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ClientRepository extends JpaRepository<Client,Long> {
	
	@Query("select c from Client c where (c.nom like:x)")
    Page<Client> findByDesignationContains(@Param("x") String mc, Pageable pageable);
	
	@Query("select c from Client c where (c.username like:x)")
    Client ChercherClientusername(@Param("x") String username);
	
	@Query("select c from Client c where (c.mail like:x)")
    Client findbymail(@Param("x") String mail);
	
	@Query("select c from Client c where (c.cin like:x OR c.mobile like:y OR c.username like:z)")
    List<Client> test(@Param("x") String cin, @Param("y") String mobile, @Param("z") String username);
	
}

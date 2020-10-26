package org.sid.dao;

import java.util.List;

import org.sid.entities.Reclamation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
	@Query("select r from Reclamation r where (r.explication like:x)")
	public Page<Reclamation> findByDesignationContains(@Param("x")String mc, Pageable pageable);
	
	@Query("select r from Reclamation r where (r.client.id like:x)")
	public Page<Reclamation> findByIDClientContains(@Param("x")Long idC, Pageable pageable);
	
	@Query("select r.idR, r.produit.designation  , r.Fixe,r.explication ,r.codeP, r.addresse from Reclamation r where (r.client.id like:x)")
	public List<Object> clientreclam(@Param("x")Long idC);
	
	
}

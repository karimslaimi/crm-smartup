package org.sid.dao;

import java.util.List;

import org.sid.entities.Client;
import org.sid.entities.technicien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TechnicienRepository  extends JpaRepository<technicien, Long> {
	@Query("select t from technicien t where (t.nom like:x)")
	public Page<technicien> findByDesignationContains(@Param("x")String mc, Pageable pageable);
	
	@Query("select t from technicien t where (t.username like:x)")
	public technicien ChercherTechnicienusername(@Param("x")String username);
	
	@Query("select CONCAT(t.nom, ' ',t.prenom) as name , count(t.id) as y from technicien t, Intervention i where i.technicien.id=t.id GROUP BY  t.id ")
	public List<Object> techstat();
	
}

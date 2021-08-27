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
    Page<Reclamation> findByDesignationContains(@Param("x") String mc, Pageable pageable);
	
	@Query("select r from Reclamation r where (r.client.id =:x) and (r.explication like :mc or r.titre like :mc)")
    Page<Reclamation> findByIDClientContains(@Param("x") Long idC,@Param("mc") String mc, Pageable pageable);
	
	@Query("select r.idR, r.explication  from Reclamation r where (r.client.id =:x)")
    List<Object> clientreclam(@Param("x") Long idC);

	//pour reseau
	@Query("select r.problem as name , count(r.idR) as y from Reclamation r where r.categorie not like 'Matériel' group by r.problem ")
	List<Object> ReclamStat();
	//pour materiel
	@Query("select r.problem as name , count(r.idR) as y from Reclamation r where r.categorie not like 'Réseau' group by r.problem ")
	List<Object> ReclamStat2();
}

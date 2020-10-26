package org.sid.dao;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.sid.entities.Intervention;
import org.sid.entities.Reclamation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InterventionRepository extends JpaRepository<Intervention, Long> {
	@Query("select i from Intervention i where (i.localisation like:x) OR (i.detaille like:x)")
	public Page<Intervention> findByDesignationContains(@Param("x")String mc, Pageable pageable);

	@Query("select i from Intervention i where (i.technicien.id like:x)")
	public Page<Intervention> findByIDtechnicienContains(@Param("x")Long idT, Pageable pageable);
	
	
	
	@Query("select i    from Intervention i where (i.dateInt>=:x)")
	public List<Intervention> findlocations(@Param("x")Date date);
	
	
	
	
	
	@Query("SELECT    monthname(i.dateInt)  as name, "
			+ "COUNT(idInt) as y \n" + 
			"FROM      Intervention i \n" + 
			
			"GROUP BY  MONTH(i.dateInt)")
	public List<Object> interstat();
	
	
	
	
}

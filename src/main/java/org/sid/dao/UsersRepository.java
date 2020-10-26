package org.sid.dao;

import org.sid.entities.users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<users,String>{
	
	

}

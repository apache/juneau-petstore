package org.apache.juneau.petstore.repository;



import org.apache.juneau.petstore.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository <User, Long>{
	
	User findByUsername(String username);
	Long deleteByUsername(String username);
}

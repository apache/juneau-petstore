package org.apache.juneau.petstore.repository;



import java.util.Optional;

import org.apache.juneau.petstore.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * TODO - Needs documentation
 */
@Repository
public interface UserRepository  extends JpaRepository <User, Long>{

	@SuppressWarnings("javadoc")
	Optional<User> findByUsername(String username);

	@SuppressWarnings("javadoc")
	Long deleteByUsername(String username);
}

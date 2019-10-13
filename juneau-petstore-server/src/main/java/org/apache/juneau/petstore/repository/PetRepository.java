package org.apache.juneau.petstore.repository;

import java.util.List;

import org.apache.juneau.petstore.dto.Pet;
import org.apache.juneau.petstore.dto.PetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository <Pet, Long> {
	@Query("select X from PetstorePet X where X.tags in :tags")
	  List<Pet> findByTags(@Param("tags") String[] tags);
	
	@Query("select X from PetstorePet X where X.status in :status")
	  List<Pet> findByStatus(@Param("status") PetStatus[] status);
}

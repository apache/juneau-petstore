package org.apache.juneau.petstore.repository;

import org.apache.juneau.petstore.dto.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository <Pet, Long> {

}

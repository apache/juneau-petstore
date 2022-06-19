package org.apache.juneau.petstore.repository;

import org.apache.juneau.petstore.dto.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * TODO - Needs documentation
 */
@Repository
public interface OrderRepository extends JpaRepository <Order, Long>{}

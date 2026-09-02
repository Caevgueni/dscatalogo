package com.devfernandes.dscatalogo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devfernandes.dscatalogo.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}

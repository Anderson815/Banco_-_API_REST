package com.anderson.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anderson.banco.model.ContaModelResponse;

@Repository
public interface ContaRepository extends JpaRepository<ContaModelResponse, String>{
}

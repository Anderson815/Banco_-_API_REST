package com.anderson.banco.repository;

import com.anderson.banco.model.response.CompraModelResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository<CompraModelResponse, Integer> {
}

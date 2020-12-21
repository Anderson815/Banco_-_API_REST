package com.anderson.banco.repository;

import com.anderson.banco.model.CompraModelResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<CompraModelResponse, Integer> {
}

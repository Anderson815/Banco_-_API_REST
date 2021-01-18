package com.anderson.banco.repository;

import com.anderson.banco.model.db.CompraModelDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //Transforma a interface em um Bean para o Spring
public interface CompraRepository extends JpaRepository<CompraModelDb, Integer> {
}

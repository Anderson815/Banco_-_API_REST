package com.anderson.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anderson.banco.model.db.ContaModelDb;

@Repository
public interface ContaRepository extends JpaRepository<ContaModelDb, String>{
    public boolean existsByRg (String rg); //verifica a existencia do Rg informado
}

package com.anderson.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anderson.banco.model.Conta;

@Repository
public interface Contas extends JpaRepository<Conta, String>{

}

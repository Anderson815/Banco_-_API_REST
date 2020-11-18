package com.anderson.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anderson.banco.model.Conta;

public interface Contas extends JpaRepository<Conta, String>{

}

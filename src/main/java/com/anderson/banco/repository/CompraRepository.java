package com.anderson.banco.repository;

import com.anderson.banco.model.CompraModelResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends CrudRepository<CompraModelResponse, Integer> {

}

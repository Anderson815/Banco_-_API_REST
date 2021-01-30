package com.anderson.banco.service;

import com.anderson.banco.exceptions.NotFoundException;
import com.anderson.banco.exceptions.RequestConstraintException;
import com.anderson.banco.model.db.ContaModelDb;
import com.anderson.banco.model.request.ContaModelRequest;
import com.anderson.banco.model.response.ContaModelResponse;
import com.anderson.banco.repository.ContaRepository;
import com.anderson.banco.service.ContaService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ContaServiceTest {
    @Mock
    private ContaRepository contaRepository;

    @InjectMocks
    private ContaService contaService;

    @Test
    public void testGetContaComSucesso(){

        String uuidConta = "444444444";

        ContaModelDb contaDb = new ContaModelDb();
        contaDb.setId("444444444");
        contaDb.setNome("Anderson");
        contaDb.setRg("123456789");
        contaDb.setValor(new BigDecimal("150.00"));

        when(contaRepository.findById(uuidConta))
                .thenReturn(Optional.of(contaDb));

        ContaModelResponse contaResponse = contaService.getConta(uuidConta);
        assertNotNull(contaResponse);
        assertEquals(uuidConta, contaResponse.getId());

    }

    @Test(expected = NotFoundException.class)
    public void testGetContaFalhaIdInexistente(){
        String uuidConta = "444444444";

        when(contaRepository.findById(uuidConta))
                .thenReturn(Optional.ofNullable(null));

        ContaModelResponse contaResponse = contaService.getConta(uuidConta);
    }

    @Test
    public void testCriarContaComSucesso(){
        ContaModelRequest contaRequest = new ContaModelRequest();
        contaRequest.setNome("Anderson");
        contaRequest.setRg("444444444");

        when(contaRepository.existsByRg(contaRequest.getRg())).thenReturn(false);

        ContaModelResponse contaResponse = contaService.criarConta(contaRequest);
        assertNotNull(contaResponse);
        assertEquals(contaRequest.getRg(), contaResponse.getRg());
        assertEquals(contaRequest.getNome(), contaResponse.getNome());
    }


    @Test(expected = RequestConstraintException.class)
    public void testCriarContaFalhaRgExistente(){

        ContaModelRequest contaRequest = new ContaModelRequest();
        contaRequest.setNome("Anderson");
        contaRequest.setRg("444444444");

        when(contaRepository.existsByRg(contaRequest.getRg())).thenReturn(true);

        ContaModelResponse contaResponse = contaService.criarConta(contaRequest);
        assertNull(contaResponse);
    }
}

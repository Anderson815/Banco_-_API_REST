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
import java.util.ArrayList;
import java.util.List;
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
    public void testGetContasComSucesso(){

        //preparo

        ContaModelDb conta1 = new ContaModelDb();
        conta1.setId("111111111");
        conta1.setNome("Anderson");
        conta1.setRg("123456789");
        conta1.setValor(new BigDecimal("450.00"));

        ContaModelDb conta2 = new ContaModelDb();
        conta2.setId("222222222");
        conta2.setNome("Beatriz");
        conta2.setRg("234567890");
        conta2.setValor(new BigDecimal("300.00"));

        ContaModelDb conta3 = new ContaModelDb();
        conta3.setId("333333333");
        conta3.setNome("Caio");
        conta3.setRg("345678901");
        conta3.setValor(new BigDecimal("150.00"));
        
        List<ContaModelDb> listaContasDb = new ArrayList<>();
        
        listaContasDb.add(conta1);
        listaContasDb.add(conta2);
        listaContasDb.add(conta3);
        
        when(contaRepository.findAll())
                .thenReturn(listaContasDb);

        //resultado experado

        ContaModelResponse contaResposta1 = new ContaModelResponse();
        contaResposta1 .setId("111111111");
        contaResposta1 .setNome("Anderson");
        contaResposta1 .setRg("123456789");
        contaResposta1 .setValor(new BigDecimal("450.00"));

        ContaModelResponse contaResposta2 = new ContaModelResponse();
        contaResposta2.setId("222222222");
        contaResposta2.setNome("Beatriz");
        contaResposta2.setRg("234567890");
        contaResposta2.setValor(new BigDecimal("300.00"));

        ContaModelResponse contaResposta3 = new ContaModelResponse();
        contaResposta3.setId("333333333");
        contaResposta3.setNome("Caio");
        contaResposta3.setRg("345678901");
        contaResposta3.setValor(new BigDecimal("150.00"));

        List<ContaModelResponse> listaEsperada = new ArrayList<>();

        listaEsperada.add(contaResposta1);
        listaEsperada.add(contaResposta2);
        listaEsperada.add(contaResposta3);


        //teste
        List<ContaModelResponse> listaAtual = contaService.getContas();
        assertArrayEquals(listaEsperada.toArray(), listaAtual.toArray());

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

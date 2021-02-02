package com.anderson.banco.service;

import com.anderson.banco.exceptions.InvalidValueException;
import com.anderson.banco.exceptions.NotFoundException;
import com.anderson.banco.exceptions.RequestConstraintException;
import com.anderson.banco.model.db.ContaModelDb;
import com.anderson.banco.model.request.ContaModelRequest;
import com.anderson.banco.model.response.ContaModelResponse;
import com.anderson.banco.repository.ContaRepository;
import com.anderson.banco.service.ContaService;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //Testes do método getConta()
    @Test
    public void testGetContaComSucesso(){

        //parêmetro
        ContaModelDb contaDb = contaDb();
        String uuidConta = contaDb.getId();

        //simulação do repository
        when(contaRepository.findById(uuidConta))
                .thenReturn(Optional.of(contaDb));

        //teste
        ContaModelResponse contaResponse = contaService.getConta(uuidConta);
        assertNotNull(contaResponse);
        assertEquals(uuidConta, contaResponse.getId());
    }

    @Test
    public void testGetContaFalhaIdInexistente(){
        //parâmetro
        String uuidConta = "111111111";

        //esperado
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Não encontramos a conta: " + uuidConta);

        //simulação do repository
        when(contaRepository.findById(uuidConta))
                .thenReturn(Optional.ofNullable(null));

        //teste
        ContaModelResponse contaResponse = contaService.getConta(uuidConta);
    }

    //Teste do método getContas()
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

        //simulação do repository
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

    //Testes do método criarConta()
    @Test
    public void testCriarContaComSucesso(){

        //parâmetro
        ContaModelRequest contaRequest = new ContaModelRequest();
        contaRequest.setNome("Anderson");
        contaRequest.setRg("111111111");

        //simulação do repository
        when(contaRepository.existsByRg(contaRequest.getRg())).thenReturn(false);

        //teste
        ContaModelResponse contaResponse = contaService.criarConta(contaRequest);
        assertNotNull(contaResponse);
        assertEquals(contaRequest.getRg(), contaResponse.getRg());
        assertEquals(contaRequest.getNome(), contaResponse.getNome());
    }

    @Test(expected = RequestConstraintException.class)
    public void testCriarContaFalhaRgExistente(){

        //parâmetro
        ContaModelRequest contaRequest = new ContaModelRequest();
        contaRequest.setNome("Anderson");
        contaRequest.setRg("444444444");

        //simulação do repository
        when(contaRepository.existsByRg(contaRequest.getRg())).thenReturn(true);

        //teste
        ContaModelResponse contaResponse = contaService.criarConta(contaRequest);
    }

    //Testes do método depositarDinheiro()
    @Test
    public void testDepositarDinheiroComSucesso(){

        //Esperado
        BigDecimal valorEsperado = new BigDecimal("1100.00");

        //Parâmetros
        String uuid = "111111111";
        BigDecimal valor = new BigDecimal("1000.00");

        //Objeto para a busca do repository
        ContaModelDb contaDb = contaDb();

        //simulação do repository
        when(contaRepository.findById(uuid))
                .thenReturn(Optional.of(contaDb));

        //teste
        ContaModelResponse contaResposta = contaService.depositarDinheiro(uuid, valor);
        BigDecimal valorAtual = contaResposta.getValor();
        assertEquals(valorEsperado, valorAtual);
    }

    @Test
    public void testDepositarDinheiroFalhaIdInexistente(){

        //parâmetro
        String uuidConta = "111111111";
        BigDecimal valor = new BigDecimal("10.00");

        //esperado
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Não encontramos a conta: " + uuidConta);

        //simulação do repository
        when(contaRepository.findById(uuidConta))
                .thenReturn(Optional.ofNullable(null));

        //teste
        ContaModelResponse contaResponse = contaService.depositarDinheiro(uuidConta, valor);
    }

    @Test
    public void testDepositarDinheiroFalhaDinheiroAbaixoDe1Centavo(){

        //Esperado
        thrown.expect(InvalidValueException.class);
        thrown.expectMessage("Valor inválido para depositar: valor abaixo de R$ 0,01");

        //Parâmetros
        String uuid = "111111111";
        BigDecimal valor = new BigDecimal("0.00");

        //Objeto para a busca do repository
        ContaModelDb contaDb = contaDb();

        //Simulação do repository
        when(contaRepository.findById(uuid))
                .thenReturn(Optional.of(contaDb));

        //Teste
        ContaModelResponse contaResposta = contaService.depositarDinheiro(uuid, valor);
    }

    @Test
    public void testDepositarDinheiroFalhaDinheiroAcimaDoLimite(){

        //Esperado
        thrown.expect(InvalidValueException.class);
        thrown.expectMessage("Valor inválido para depositar: valor acima de R$ 5000,00");

        //Parâmetros
        String uuid = "111111111";
        BigDecimal valor = new BigDecimal("5000.01");

        //Objeto para a simulação
        ContaModelDb contaDb = contaDb();

        //Simulação do repository
        when(contaRepository.findById(uuid))
                .thenReturn(Optional.of(contaDb));

        //Teste
        ContaModelResponse contaResponse = contaService.depositarDinheiro(uuid, valor);
    }

    //Testes do método sacarDinheiro()
    @Test
    public void testSacarDinheiroComSucesso(){

        //Esperado
        BigDecimal valorEsperado = new BigDecimal("79.45");

        //parâmetro
        String uuid = "111111111";
        BigDecimal valor = new BigDecimal("20.55");

        //Objeto para a simulação
        ContaModelDb contaDb = contaDb();

        //Simulação
        when(contaRepository.findById(uuid))
                .thenReturn(Optional.of(contaDb));

        //Teste
        ContaModelResponse contaResponse = contaService.sacarDinheiro(uuid, valor);
        BigDecimal valorAtual = contaResponse.getValor();
        assertEquals(valorEsperado, valorAtual);
    }

    @Test
    public void testSacarDinheiroFalhaIdInexistente(){

        //Parâmetros
        String uuid = "1111111111";
        BigDecimal valor = new BigDecimal("100.00");

        //Esperado
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Não encontramos a conta: " + uuid);

        //Objeto para simulação
        ContaModelDb contaDb = contaDb();

        //Simulação
        when(contaRepository.findById(uuid))
                .thenReturn(Optional.ofNullable(null));

        //Teste
        ContaModelResponse contaResponse = contaService.sacarDinheiro(uuid, valor);
    }

    @Test
    public void testSacarDinheiroFalhaDinheiroAbaixoDe1Centavo(){

        //Parâmetros
        String uuid = "111111111";
        BigDecimal valor = new BigDecimal("0.00");

        //Esperado
        thrown.expect(InvalidValueException.class);
        thrown.expectMessage("Valor inválido para sacar: valor abaixo de R$ 0,01");

        //Objeto para a simulação
        ContaModelDb contaDb = contaDb();

        //Simulação
        when(contaRepository.findById(uuid))
                .thenReturn(Optional.of(contaDb));

        //Teste
        ContaModelResponse contaResponse = contaService.sacarDinheiro(uuid, valor);
    }

    @Test
    public void testSacarDinheiroFalhaDinheiraAcimaDoDepositado(){

        //Parâmetros
        String uuid = "111111111";
        BigDecimal valor = new BigDecimal("100.01");

        //Esperado
        thrown.expect(InvalidValueException.class);
        thrown.expectMessage("Valor inválido para sacar: valor de saque acima do valor depositado na conta");

        //Objeto para simulação
        ContaModelDb contaDb = contaDb();

        //Simulação
        when(contaRepository.findById(uuid))
                .thenReturn(Optional.of(contaDb));

        //Teste
        ContaModelResponse contaResponse = contaService.sacarDinheiro(uuid, valor);
    }

    //Métodos preparadores
    private ContaModelDb contaDb(){
        ContaModelDb contaDb = new ContaModelDb();
        contaDb.setId("111111111");
        contaDb.setNome("Anderson");
        contaDb.setRg("123456789");
        contaDb.setValor(new BigDecimal("100.00"));

        return contaDb;
    }
}

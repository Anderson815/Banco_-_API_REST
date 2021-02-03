<h1>Banco - API REST</h1>
<p>
Este projeto foi um desafio sugerido pelo <a href="https://www.linkedin.com/in/cesar-sales/">Cesar Sales</a> com o objetivo de eu apreder tecnologias, ferramentas e técnicas modernas voltadas ao desenvolvimento backend por meio da linguaguem Java (lista das ferramentas na seção <a href="#habilidade">Habilidades Usadas</a>), além disso ele me deu muitas dicas e orientações ao longo do desenvolvimento.
</p>
<p>
Esta é uma API de pagamento, eu chamo ela de banco, com ela é possível fazer operações básicas de um banco, como: depositar, sacar, trasferir, comprar e entre outras funcionalidades (todas as funcionalidades estão na seção <a href="#endpoints">Endpoint</a>). 
</p>
<p>  
Embora seja um projeto simples não deixa de ser completo, pois tem relacionamento entre entidades, há validação para as requisições, status code adequado para a resposta, um padrão de exceções, separção por camadas e muitas outras coisas (os meios para testar a aplicação está na seção <a href="#acesso">Para acessar a API</a>) você pode verificar o código. 
</p>

<img src="Midia/Banco_-_API_REST.png">


<h2 id="endpoints">:dart: Endpoints</h2>

| Endpoint | Método/Verbo | Endpoint                          |  Funcionalidade                                                        |
|:------:|--------------|-----------------------------------|------------------------------------------------------------------------|
| 01   | GET          | /conta                            | Retorna todas as contas cadastradas no banco                           |
| 02   | GET          | /conta/{uuid}                     | Retorna uma conta específica através do ID informado                   |
| 03   | POST         | /conta                            | Cadastra uma nova conta no banco                                       |
| 04   | PUT          | /conta/{uuid}/deposito            | Deposita dinheiro na conta correpondente ao ID informado               |
| 05   | PUT          | /conta/{uuid}/saque               | Saca dinheiro na conta correpondente ao ID informado                   |
| 06   | PUT          | /conta/{uuidRetira}/transferencia | Transfere dinheiro de uma conta para outra através dos seus IDs        |
| 07   | DELETE       | /conta/{uuid}                     | Deleta uma conta do banco através do seu ID                            |
| 08   | GET          | /conta/{uuid}/compra              | Retorna todas as compras da conta do ID informado                      |
| 09   | GET          | /conta/{uuid}/compra/{id_compra}  | Retorna uma compra especifíca através do seu ID e da Conta responsável |
| 10   | POST         | /conta/{uuid}/compra              | Cria uma nova compra através do ID da conta responsável                |

<br/>

| <p id="1">GET</p> | /conta | Retorna todas as contas cadastradas no banco | 
|---|---|----------------------------------------------|

<h2 id="habilidade">:mortar_board: Habilidades usadas</h2>
<ul>
  <li>Java</li>
  <li>MySQL (para o desenvolvimento da API)</li>
  <li>PostgreSQL (para fazer o deploy da API no heroku)</li>
  <li>Spring Framework</li>
  <li>Spring Boot</li>
  <li>Spring MVC</li>
  <li>Spring Data Jpa</li>
  <li>Maven</li>
  <li>JUnit</li>
  <li>Mokito</li>
  <li>Swagger</li>
  <li>REST</li>
</ul>

<h2 id="acesso">:checkered_flag: Para acessar a API</h2>
<p>Basta clicar <a href="https://bancoapirest.herokuapp.com/swagger-ui.html#/Conta">aqui</a> para ver e fazer os teste da API</p>
<p>Caso você queira usar o Postman para testar é só usar este domínio: https://bancoapirest.herokuapp.com</p>

<h2>:pray: Ajuda</h2>

  <p>
  Se você gostou do projeto, por favor não esqueça de:
  </p>
  
  <ol>
    <li>Dar sua recomendação, das habilidade utilizadas nesse projeto, no <a href="linkedin.com/in/anderson-correia">meu LinkedIn</a>.</li>
    <li>Caso você tenha algum comentário, feedback ou sugestão me mande uma mensagem também no <a href="linkedin.com/in/anderson-correia">meu LinkedIn</a>.</li>
  </ol>
  
  <p>
  Muito obrigado pela sua colaboração !!!
  </p>

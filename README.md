<h1>Banco - API REST</h1>
<p>
Este projeto foi um desafio sugerido pelo <a href="https://www.linkedin.com/in/cesar-sales/">Cesar Sales</a> com o objetivo de eu apreder tecnologias, ferramentas e técnicas modernas voltadas ao desenvolvimento backend por meio da linguaguem Java (listas destas ferramentas na seção <a href="#habilidade">Habilidades Usadas</a>), além disso ele me deu muitas dicas e orientações ao longo do desenvolvimento.
</p>
<p>
</p>
quando? por que? o que? como?  
</p>
<img src="Midia/Banco_-_API_REST.png">


<h2>:dart: Endpoints e suas funcionalidades</h2>

| Método/Verbo | Endpoint                          |  Funcionalidade                                                        |
|--------------|-----------------------------------|------------------------------------------------------------------------|
| GET          | /conta                            | Retorna todas as contas cadastradas no banco                           |
| GET          | /conta/{uuid}                     | Retorna uma conta específica através do ID informado                   |
| POST         | /conta                            | Cadastra uma nova conta no banco                                       |
| PUT          | /conta/{uuid}/deposito            | Deposita dinheiro na conta correpondente ao ID informado               |
| PUT          | /conta/{uuid}/saque               | Saca dinheiro na conta correpondente ao ID informado                   |
| PUT          | /conta/{uuidRetira}/transferencia | Transfere dinheiro de uma conta para outra através dos seus IDs        |
| DELETE       | /conta/{uuid}                     | Deleta uma conta do banco através do seu ID                            |
| GET          | /conta/{uuid}/compra              | Retorna todas as compras da conta do ID informado                      |
| GET          | /conta/{uuid}/compra/{id_compra}  | Retorna uma compra especifíca através do seu ID e da Conta responsável |
| POST         | /conta/{uuid}/compra              | Cria uma nova compra através do ID da conta responsável                |


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

<h2>:checkered_flag: Para acessar a API</h2>
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

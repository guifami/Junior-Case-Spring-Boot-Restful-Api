# Java Spring Boot RESTful API - Junior Case
### Tecnologias: Java, MySql, Docker, Github Actions e Swagger.
<br>

# Banco de Dados
### Foi utilizado o Flyway como ferramenta de IaC para provisionar a Infraestrutura do Banco de Dados.
<br>

# Endpoints de Dados Pessoais

![image](https://github.com/guifami/Junior-Case-Spring-Boot-Restful-Api/assets/93688391/159396ca-2a7c-4039-a44e-d268cb75f8b0)

### GET - /api/person/v1/{id} | Consultar um cadastro.
### GET - /api/person/v1 | Listar todos os cadastros.
### POST - /api/person/v1 | Efetuar um cadastramento.
### PATCH - /api/person/v1/{id} | Atualizar um dos dados do cadastro (idade).
### DELETE - /api/person/v1/{id} |  Efetuar a exclusão do cadastro.
<br>

### A API RESTful respeita o último nível de maturidade de Richardson:
![image](https://github.com/guifami/Junior-Case-Spring-Boot-Restful-Api/assets/93688391/b0471b3a-e55a-4930-8a39-533a5a5dba32)


### Foi implementado 3 três tipos de Accepts: JSON, XML e YAML além dos links de HATEOAS e paginação, atigindo a Glória do REST. 
A Aplicação foi construída com uma Arquitetura um pouco mais convencional devido a dificuldades durante a implementação prática da Arquitetura Hexagonal, que possivelmente atrasaria a implementação dos demais requisitos, mas claro, ainda extremamente sólida e eficiente.
<br><br>
Além dos <b>testes unitários</b>, também foram adicionados <b>testes de integração</b> para melhor cobertura da aplicação como um todo, incluindo testes de Cors, Converter, Swagger, e Controller (JSON, XML e YAML). <b>Totalizando 34 Testes Automatizados</b>, consequentemente, blindando a API :).
<br><br>
![image](https://github.com/guifami/Junior-Case-Spring-Boot-Restful-Api/assets/93688391/d7ef826d-0b91-4f88-8079-dd150b27597c)
<br><br>

# Docker
![image](https://github.com/guifami/Junior-Case-Spring-Boot-Restful-Api/assets/93688391/e780a195-eb55-441e-a828-5d6704b4d6d0)

Tanto a API quanto o Banco de Dados MySql foram Dockerizados durante o desenvolvimento e utiliza uma Stack do <b>Docker Compose</b> para provisionamento automático.
Para os Testes de Integração, são construídos 2 containers temporários para execução dos testes, e assim que concluídos, ambos são destruídos.
Além disso, foi construído uma <b>Continuous Integration</b> com GitHub Actions para assim que "commitar" uma nova alteração no repositório Git, ela constrói as imagens Docker e as disponibiliza no Docker Hub, visível na aba <b>"Actions"</b>.
<br><br>
Para testes e melhor Documentação da API, adicionei o Swagger.
<br>
<b>Link do Swagger quando a aplicação estiver executando: http://localhost/swagger-ui/index.html#/</b>
<br><br>

# Logs
Os Logs da aplicação <b>main</b> são gerados conforme a interação com os Endpoints e persistidos na tabela <b>tb_logs</b>.
<br>
Quanto aos Logs de <b>teste</b>, são gerados em tempo de execução e é exibido conforme é testado.

# Execução
Para rodar o projeto, após clonado o repositório, no diretório onde se encontra o arquivo <b>pom.xml</b> execute o seguinte comando:
<br>
<b>mvn clean package -DskipTests</b>
<br>
Em seguida, vá ao diretório onde contém o arquivo <b>docker-compose.yml</b> e execute o seguinte comando:
<br>
<b>docker compose up -d --build</b>
<br>
Dessa forma, a aplicação já estará funcionando e pode ser executado os testes automatizados da maneira como preferir :).

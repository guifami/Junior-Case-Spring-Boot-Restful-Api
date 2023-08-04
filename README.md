# Java Spring Boot RESTful API - Junior Case
### Tecnologias: Java, MySql, Docker, Github Actions e Swagger.
<br>

# Banco de Dados
<br>

![image](https://github.com/guifami/Java-Spring-Boot-RESTful-Api/assets/93688391/c71261bb-8597-4fac-824f-58f223dd787a)
### Foi utilizado o Flyway como ferramenta de IaC para provisionar a Infraestrutura do Banco de Dados.
<br>

# Endpoints de Dados Pessoais
![image](https://github.com/guifami/Java-Spring-Boot-RESTful-Api/assets/93688391/24d0a354-bf42-4bb5-a57e-e2ef5a32b6c9)
<br><br>
### GET - /api/person/v1/{id} | Consultar um cadastro.
### GET - /api/person/v1 | Listar todos os cadastros.
### POST - /api/person/v1 | Efetuar um cadastramento.
### PATCH - /api/person/v1/{id} | Atualizar um dos dados do cadastro (idade).
### DELETE - /api/person/v1/{id} |  Efetuar a exclusão do cadastro.
<br>

### A API RESTful respeita o último nível de maturidade de Richardson:
![image](https://github.com/guifami/Java-Spring-Boot-RESTful-Api/assets/93688391/5c4bef5f-d92a-47cc-8682-33fbf922da98)

### Foi implementado 3 três tipos de Accepts: JSON, XML e YAML além dos links de HATEOAS e paginação, atigindo a Glória do REST. 
A Aplicação foi construída com uma Arquitetura um pouco mais convencional devido a dificuldades durante a implementação prática da Arquitetura Hexagonal, que possivelmente atrasaria a implementação dos demais requisitos, mas claro, ainda extremamente sólida e eficiente.
<br><br>
Além dos <b>testes unitários</b>, também foram adicionados <b>testes de integração</b> para melhor cobertura da aplicação como um todo, incluindo testes de Cors, Converter, Swagger, e Controller (JSON, XML e YAML). <b>Totalizando 34 Testes Automatizados</b>, consequentemente, blindando a API :).
<br><br>
![image](https://github.com/guifami/Junior-Case-Spring-Boot-Restful-Api/assets/93688391/d7ef826d-0b91-4f88-8079-dd150b27597c)
<br><br>

# Docker
![image](https://github.com/guifami/Junior-Case-Spring-Boot-Restful-Api/assets/93688391/1dc931a0-658f-4e67-946e-7d520fae79d1)

Tanto a API quanto o Banco de Dados MySql foram Dockerizados durante o desenvolvimento e utiliza uma Stack do <b>Docker Compose</b> para provisionamento automático.
Para os Testes de Integração, são construídos 2 containers temporários para execução dos testes, e assim que concluídos, ambos são destruídos.
Além disso, foi construído uma <b>Continuous Integration</b> com GitHub Actions para assim que "commitar" uma nova alteração no repositório Git, ela constrói as imagens Docker e as disponibiliza no Docker Hub, visível na aba <b>"Actions"</b>. A Imagem da Aplicação Spring sobe sem logs de erro mas testei e não consegui acessar os edpoints pela porta configurada (80), contudo, em caso da aplicação Spring não executar, é necessário clonar o repostório, executar o <b>docker compose up -d --build</b>, e executar a aplicação Spring localmente, dessa forma, o banco de dados estará no Docker, e a aplicação localmente (endpoints acessíveis na porta 8888).
<br><br>

# Logs
Os Logs da aplicação <b>main</b> são gerados conforme a interação com os Endpoints e persistidos na tabela <b>tb_logs</b>.
<br>
Quanto aos Logs de <b>teste</b>, são gerados em tempo de execução e é exibido conforme é testado.

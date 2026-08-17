# API Cursos

API REST desenvolvida em Java com Spring Boot para gerenciamento de cursos de uma empresa fictícia de cursos de programação.

O projeto foi desenvolvido com foco em boas práticas de desenvolvimento de APIs REST, separação de responsabilidades, validação de dados, tratamento centralizado de exceções, testes automatizados, containerização e integração contínua.

---

## 🚀 Tecnologias

- Java 17
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Hibernate
- PostgreSQL
- Lombok
- Bean Validation
- SpringDoc OpenAPI / Swagger
- JUnit 5
- Mockito
- MockMvc
- Testcontainers
- Gradle
- Docker
- Docker Compose
- GitHub Actions
- GitHub Container Registry (GHCR)

---

## 📊 Status

![CI](https://github.com/SirAndreCabral/api-curso/actions/workflows/ci.yaml/badge.svg)

![Docker Publish](https://github.com/SirAndreCabral/api-curso/actions/workflows/docker-publish.yml/badge.svg)

Projeto funcional desenvolvido para estudo e portfólio.

Atualmente o projeto possui:

- API REST funcional;
- PostgreSQL;
- validação de dados;
- tratamento centralizado de exceções;
- documentação com Swagger/OpenAPI;
- testes unitários;
- testes de Controller;
- testes de integração com Testcontainers;
- Docker;
- Docker Compose;
- CI com GitHub Actions;
- build e publicação automática da imagem Docker no GHCR.

---

## 🏗️ Arquitetura

O projeto utiliza uma arquitetura baseada na separação de responsabilidades:

text
src/
├── main/
│   └── java/
│       └── org.example.apicursos/
│           ├── controller/
│           ├── dto/
│           ├── exception/
│           ├── mapper/
│           ├── model/
│           ├── repository/
│           └── service/
│
└── test/
    └── java/
        └── org.example.apicursos/
            ├── CourseControllerTest
            ├── CourseIntegrationTest
            ├── CourseMapperTest
            └── CourseServiceTest

Responsabilidade das camadas

Controller

Responsável pelos endpoints HTTP, recebimento das requisições, validações e retorno das respostas.

Service

Contém as regras de negócio da aplicação.

Repository

Responsável pelo acesso aos dados através do Spring Data JPA.

DTO

Define os dados que entram e saem da API, evitando expor diretamente as entidades.

Mapper

Responsável pela conversão entre entidades e DTOs.

Exception

Centraliza as exceções específicas da aplicação e seu tratamento.

📚 Funcionalidades

A API permite:

Criar um curso;
Listar todos os cursos;
Filtrar cursos por nome;
Filtrar cursos por categoria;
Filtrar cursos por nome e categoria;
Buscar um curso pelo ID;
Atualizar um curso;
Excluir um curso;
Ativar/desativar um curso;
Validar dados recebidos pela API;
Retornar erros HTTP apropriados.
🔗 Endpoints
Criar curso
POST /course/create_course

Request:

{
  "name": "Java",
  "category": "Backend"
}

Resposta:

{
  "id": "uuid",
  "name": "Java",
  "category": "Backend",
  "active": true
}

Retorno: 201 Created

Listar cursos
GET /course/list_courses

Retorno: 200 OK

Também é possível utilizar filtros.

Por nome
GET /course/list_courses?name=Java
Por categoria
GET /course/list_courses?category=Backend
Por nome e categoria
GET /course/list_courses?name=Java&category=Backend
Buscar curso por ID
GET /course/list_course/{id}

Retorno: 200 OK

Caso o curso não exista:

404 Not Found
Atualizar curso
PUT /course/update_course/{id}

Request:

{
  "name": "Java Spring",
  "category": "Backend"
}

Retorno: 200 OK

Excluir curso
DELETE /course/delete_course/{id}

Retorno:

204 No Content
Ativar ou desativar curso
PATCH /course/patch/{id}/active

O endpoint alterna o estado do atributo active.

true  → false
false → true

Retorno: 200 OK

🗄️ Banco de dados

O projeto utiliza PostgreSQL.

A entidade CourseModel possui os seguintes campos:

Campo	Tipo	Descrição
id	UUID	Identificador único
name	String	Nome do curso
category	String	Categoria do curso
active	Boolean	Indica se o curso está ativo
createdAt	Timestamp	Data de criação
updatedAt	Timestamp	Data da última atualização

Tabela:

tb_course
⚙️ Configuração

A aplicação utiliza variáveis de ambiente para configurar a conexão com o banco de dados.

spring.application.name=api_cursos


spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver


spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
Variáveis de ambiente
DB_URL
DB_USERNAME
DB_PASSWORD

Exemplo:

DB_URL=jdbc:postgresql://localhost:5432/api_cursos
DB_USERNAME=postgres
DB_PASSWORD=postgres

As credenciais não devem ser armazenadas diretamente no código ou versionadas no repositório.

▶️ Executando localmente
Pré-requisitos

Para executar sem Docker, é necessário possuir:

Java 17 ou superior;
PostgreSQL;
Git.

Clone o repositório:

git clone https://github.com/SirAndreCabral/api-curso.git

Entre na pasta:

cd api-curso

Configure um banco PostgreSQL:

Database: api_cursos
Username: postgres
Password: postgres
Port: 5432

Configure as variáveis de ambiente:

DB_URL=jdbc:postgresql://localhost:5432/api_cursos
DB_USERNAME=postgres
DB_PASSWORD=postgres

Execute a aplicação:

Linux / macOS / Git Bash
./gradlew bootRun
Windows
gradlew.bat bootRun

A API ficará disponível em:

http://localhost:8080
🐳 Executando com Docker

O projeto possui Dockerfile e Docker Compose para facilitar a execução da aplicação e do PostgreSQL.

Pré-requisitos
Docker
Docker Compose

Configure um arquivo .env local:

DB_USERNAME=postgres
DB_PASSWORD=postgres

O arquivo .env não deve ser versionado no Git.

Execute:

docker compose up --build

A aplicação ficará disponível em:

http://localhost:8080

O PostgreSQL será executado em:

localhost:5432

Para executar em segundo plano:

docker compose up --build -d

Para parar os containers:

docker compose down
📦 Docker Image

A imagem Docker da aplicação é construída e publicada automaticamente no GitHub Container Registry (GHCR) através do GitHub Actions.

O processo ocorre quando há um push na branch main.

A imagem utiliza as seguintes tags:

latest
<commit-sha>

A imagem pode ser encontrada no GitHub Container Registry do projeto.

📖 Swagger / OpenAPI

A API possui documentação através do Swagger/OpenAPI.

Após iniciar a aplicação, acesse:

http://localhost:8080/swagger-ui/index.html

Através da interface é possível:

visualizar os endpoints;
consultar os modelos de requisição e resposta;
executar requisições;
verificar códigos HTTP;
testar os recursos da API.
🧪 Testes

O projeto possui testes unitários, testes de Controller e testes de integração.

Testes unitários

São utilizados:

JUnit 5;
Mockito.

A camada Service é testada isoladamente utilizando mocks para o Repository.

Testes do Controller

Os endpoints são testados utilizando:

@WebMvcTest;
MockMvc;
Mockito.

São verificados, entre outros:

códigos HTTP;
JSON retornado;
validações;
parâmetros;
chamadas para a Service;
IDs inválidos.
Testes de integração

A integração com PostgreSQL é testada utilizando Testcontainers.

Durante os testes é criado um container PostgreSQL real, permitindo testar:

persistência de cursos;
consultas por nome;
consultas por categoria;
consultas combinadas;
integração entre Spring Data JPA, Hibernate e PostgreSQL.
Executar os testes
./gradlew test

No Windows:

gradlew.bat test

Resultado esperado:

BUILD SUCCESSFUL
🔄 CI/CD

O projeto utiliza GitHub Actions para automatizar testes e construção da imagem Docker.

CI

A pipeline de CI é executada em:

push na main;
push na develop;
Pull Requests para main;
Pull Requests para develop.

O processo executa:

Push / Pull Request
        ↓
GitHub Actions
        ↓
Checkout
        ↓
Java 17
        ↓
Gradle
        ↓
Testes automatizados

Caso algum teste falhe, a pipeline é interrompida.

Docker Publish

Após o CI, a imagem Docker é construída e publicada no GitHub Container Registry.

Fluxo:

Push na main
     ↓
   CI
     ↓
 Testes
     ↓
Docker Build
     ↓
Docker Image
     ↓
GHCR

Dessa forma, cada alteração aprovada na main pode gerar uma nova versão da imagem Docker.

🔐 Segurança

As credenciais do banco de dados são configuradas através de variáveis de ambiente.

Arquivos contendo informações sensíveis, como:

.env

não devem ser enviados para o repositório.

O projeto também utiliza o GITHUB_TOKEN fornecido pelo GitHub Actions para autenticação no GitHub Container Registry.

🔍 Boas práticas utilizadas

O projeto utiliza:

DTOs para entrada e saída de dados;
separação entre Controller, Service e Repository;
validação de dados;
tratamento centralizado de exceções;
testes unitários;
testes de Controller;
testes de integração;
PostgreSQL real nos testes de integração;
Testcontainers;
documentação da API com OpenAPI;
variáveis de ambiente;
Docker;
Docker Compose;
CI com GitHub Actions;
publicação automatizada de imagens Docker;
GitHub Container Registry.
📌 Próximas etapas

Algumas melhorias que podem ser implementadas futuramente:

Aumentar cobertura de testes;
Adicionar JaCoCo para análise de cobertura;
Configurar proteção da branch main;
Adicionar Dependabot;
Melhorar observabilidade e logging;
Adicionar paginação à listagem de cursos;
Adicionar versionamento da API;
Melhorar configurações específicas para produção.
👨‍💻 Autor

André Cabral

Projeto desenvolvido como estudo prático de Java e Spring Boot, 
com foco no desenvolvimento de APIs REST, persistência de dados, 
testes automatizados, containerização e práticas de integração contínua.

GitHub:

https://github.com/SirAndreCabral
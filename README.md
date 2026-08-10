# API Cursos

API REST desenvolvida em Java com Spring Boot para gerenciamento de cursos de uma empresa fictícia de cursos de programação.

O projeto foi desenvolvido com foco em boas práticas de desenvolvimento de APIs REST, separação de responsabilidades, validação de dados, tratamento de exceções e testes automatizados.

## 🚀 Tecnologias

* Java 17
* Spring Boot
* Spring Web MVC
* Spring Data JPA
* Hibernate
* PostgreSQL
* Lombok
* Bean Validation
* SpringDoc OpenAPI / Swagger
* JUnit 5
* Mockito
* MockMvc
* Testcontainers
* Gradle

## 🏗️ Arquitetura

O projeto utiliza uma arquitetura baseada na separação de responsabilidades:

```text
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
```

### Responsabilidade das camadas

**Controller**

Responsável pelos endpoints HTTP, recebimento das requisições, validações e retorno das respostas.

**Service**

Contém as regras de negócio da aplicação.

**Repository**

Responsável pelo acesso aos dados através do Spring Data JPA.

**DTO**

Define os dados que entram e saem da API, evitando expor diretamente a entidade em todas as operações.

**Mapper**

Responsável pela conversão entre entidades e DTOs.

**Exception**

Centraliza as exceções específicas da aplicação e seu tratamento.

---

## 📚 Funcionalidades

A API permite:

* Criar um curso
* Listar todos os cursos
* Filtrar cursos por nome
* Filtrar cursos por categoria
* Filtrar cursos por nome e categoria
* Buscar um curso pelo ID
* Atualizar um curso
* Excluir um curso
* Ativar/desativar um curso
* Validar dados recebidos pela API
* Retornar erros HTTP apropriados

---

## 🔗 Endpoints

### Criar curso

```http
POST /course/create_course
```

Request:

```json
{
  "name": "Java",
  "category": "Backend"
}
```

Resposta:

```json
{
  "id": "uuid",
  "name": "Java",
  "category": "Backend",
  "active": true
}
```

Retorno: `201 Created`

---

### Listar cursos

```http
GET /course/list_courses
```

Retorno: `200 OK`

Também é possível utilizar filtros.

#### Por nome

```http
GET /course/list_courses?name=Java
```

#### Por categoria

```http
GET /course/list_courses?category=Backend
```

#### Por nome e categoria

```http
GET /course/list_courses?name=Java&category=Backend
```

---

### Buscar curso por ID

```http
GET /course/list_course/{id}
```

Retorno: `200 OK`

Caso o curso não exista:

```text
404 Not Found
```

---

### Atualizar curso

```http
PUT /course/update_course/{id}
```

Request:

```json
{
  "name": "Java Spring",
  "category": "Backend"
}
```

Retorno: `200 OK`

---

### Excluir curso

```http
DELETE /course/delete_course/{id}
```

Retorno:

```text
204 No Content
```

---

### Ativar ou desativar curso

```http
PATCH /course/patch/{id}/active
```

O endpoint alterna o estado do atributo `active`.

Por exemplo:

```text
true → false
false → true
```

Retorno: `200 OK`

---

## 🗄️ Banco de dados

O projeto utiliza PostgreSQL.

A entidade `CourseModel` possui os seguintes campos:

| Campo       | Tipo      | Descrição                    |
| ----------- | --------- | ---------------------------- |
| `id`        | UUID      | Identificador único          |
| `name`      | String    | Nome do curso                |
| `category`  | String    | Categoria do curso           |
| `active`    | Boolean   | Indica se o curso está ativo |
| `createdAt` | Timestamp | Data de criação              |
| `updatedAt` | Timestamp | Data da última atualização   |

Tabela:

```text
tb_course
```

---

## ⚙️ Configuração

As configurações do banco podem ser fornecidas através de variáveis de ambiente.

Exemplo:

```properties
spring.application.name=api_cursos

spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/api_cursos}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:postgres}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Os valores após `:` são valores padrão utilizados quando as variáveis de ambiente não estão configuradas.

### Variáveis disponíveis

```text
DB_URL
DB_USERNAME
DB_PASSWORD
```

Exemplo:

```text
DB_URL=jdbc:postgresql://localhost:5432/api_cursos
DB_USERNAME=postgres
DB_PASSWORD=postgres
```

> Em ambientes de produção, recomenda-se utilizar variáveis de ambiente ou um serviço de gerenciamento de secrets em vez de armazenar credenciais diretamente no código.

---

## ▶️ Como executar

### Pré-requisitos

Antes de executar o projeto, é necessário ter instalado:

* Java 17 ou superior
* PostgreSQL
* Git

Clone o repositório:

```bash
git clone <URL_DO_REPOSITORIO>
```

Entre na pasta:

```bash
cd api-cursos
```

Configure o banco PostgreSQL:

```text
Database: api_cursos
Username: postgres
Password: postgres
Port: 5432
```

Depois execute a aplicação:

```bash
./gradlew bootRun
```

No Windows:

```bash
gradlew.bat bootRun
```

A API ficará disponível em:

```text
http://localhost:8080
```

---

## 📖 Swagger / OpenAPI

A API possui documentação através do Swagger/OpenAPI.

Após iniciar a aplicação, a documentação pode ser acessada pelo Swagger UI.

```text
http://localhost:8080/swagger-ui/index.html
```

Através da interface é possível visualizar os endpoints e realizar requisições diretamente na aplicação.

---

## 🧪 Testes

O projeto possui testes unitários, testes do Controller e testes de integração.

### Testes unitários

Foram utilizados:

* JUnit 5
* Mockito

A camada Service é testada isoladamente utilizando mocks para o Repository.

### Testes do Controller

Os endpoints são testados utilizando:

* `@WebMvcTest`
* MockMvc
* Mockito

São verificados, entre outros:

* códigos HTTP;
* JSON retornado;
* validações;
* parâmetros;
* chamadas para a Service;
* IDs inválidos.

### Testes de integração

A integração com o PostgreSQL é testada utilizando Testcontainers.

Durante os testes é criado um container PostgreSQL real, permitindo testar:

* persistência de cursos;
* consultas por nome;
* consultas por categoria;
* consultas combinadas;
* integração entre Spring Data JPA, Hibernate e PostgreSQL.

### Executar os testes

```bash
./gradlew test
```

No Windows:

```bash
gradlew.bat test
```

Resultado esperado:

```text
BUILD SUCCESSFUL
```

---

## 🔍 Qualidade e boas práticas

O projeto utiliza:

* DTOs para entrada e saída de dados;
* separação entre Controller, Service e Repository;
* validação de dados;
* tratamento centralizado de exceções;
* testes unitários;
* testes de integração;
* PostgreSQL real nos testes de integração;
* documentação da API com OpenAPI;
* variáveis de ambiente para configuração do banco.

---

## 🐳 Docker

A utilização de Docker para a aplicação e o banco de dados está prevista como próxima etapa de configuração do projeto.

A proposta será permitir executar a API e o PostgreSQL através de Docker Compose, reduzindo a necessidade de configuração manual do ambiente.

---

## 🔄 CI/CD

A próxima etapa de automação do projeto será a configuração de uma pipeline de CI/CD.

A pipeline terá como objetivo executar automaticamente, a cada alteração no repositório:

```text
Push / Pull Request
        ↓
GitHub Actions
        ↓
Build
        ↓
Testes
        ↓
Resultado
```

Posteriormente, a pipeline poderá ser expandida para criação de imagem Docker e deploy automático.

---

## 📌 Status do projeto

**Em desenvolvimento / projeto de estudo e portfólio.**

Funcionalidades principais da API, testes automatizados, documentação e integração com PostgreSQL já estão implementados.

Próximas etapas:

* [ ] Docker / Docker Compose
* [ ] CI com GitHub Actions
* [ ] Build automático da aplicação
* [ ] Pipeline de deploy
* [ ] Melhorias de configuração para produção

---

## 👨‍💻 Autor

**André Cabral**

Projeto desenvolvido como estudo prático de Java e Spring Boot, com foco no desenvolvimento de APIs REST, persistência de dados, testes automatizados e boas práticas de desenvolvimento.

# API de Categorias

Uma API RESTful desenvolvida com Spring Boot para gerenciamento de categorias. Esta aplicação fornece endpoints completos para operações CRUD (Create, Read, Update, Delete) em um banco de dados PostgreSQL.

## 🚀 Funcionalidades

- **Gerenciamento de Categorias**: Operações completas de CRUD para cadastro de categorias
- **Validação de Dados**: Validação automática de campos obrigatórios e tamanho de caracteres
- **Tratamento de Exceções**: Tratamento global de erros com mensagens claras
- **Nome Único**: Garantia de unicidade de nome no cadastro de categorias
- **DTOs**: Separação clara entre entidades de banco e objetos de transferência de dados
- **Testes Unitários**: Cobertura de testes para a camada de serviço

## 🛠️ Tecnologias Utilizadas

- Java 17
- Spring Boot 4.1.0
- Spring Data JPA
- Spring Web MVC
- Spring Validation
- PostgreSQL
- Lombok
- Maven

## 📋 Pré-requisitos

- Java 17 ou superior instalado
- Maven 3.6+ instalado
- PostgreSQL 12+ instalado e rodando
- IDE de sua preferência (IntelliJ IDEA, Eclipse, VS Code, etc.)

## 🔧 Instalação e Configuração

### 1. Clone o repositório

```bash
git clone <repository-url>
cd API-de-Categorias
```

### 2. Configure o Banco de Dados

Crie um banco de dados PostgreSQL:

```sql
CREATE DATABASE api_categorias;
```

### 3. Configure as Credenciais

Edite o arquivo `src/main/resources/application.properties` ou configure as variáveis de ambiente:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/api_categorias
spring.datasource.username=postgres
spring.datasource.password=sua_senha
```

Ou usando variáveis de ambiente:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/api_categorias
export DB_USERNAME=postgres
export DB_PASSWORD=sua_senha
```

### 4. Compile o Projeto

```bash
./mvnw clean install
```

Ou usando Maven diretamente:

```bash
mvn clean install
```

## ▶️ Executando a Aplicação

### Via Maven

```bash
./mvnw spring-boot:run
```

### Via JAR

```bash
java -jar target/API-de-Categorias-0.0.1-SNAPSHOT.jar
```

A aplicação estará disponível em: `http://localhost:8080`

## 📡 Endpoints da API

### Criar Categoria

```http
POST /categorias
Content-Type: application/json

{
  "nome": "Eletrônicos"
}
```

**Response: 201 Created**

```json
{
  "id": 1,
  "nome": "Eletrônicos"
}
```

### Listar Todas as Categorias

```http
GET /categorias
```

**Response: 200 OK**

```json
[
  {
    "id": 1,
    "nome": "Eletrônicos"
  }
]
```

### Buscar Categorias por Nome

```http
GET /categorias?nome=Eletrônicos
```

**Response: 200 OK**

```json
[
  {
    "id": 1,
    "nome": "Eletrônicos"
  }
]
```

### Buscar Categoria por ID

```http
GET /categorias/{id}
```

**Response: 200 OK**

```json
{
  "id": 1,
  "nome": "Eletrônicos"
}
```

### Atualizar Categoria

```http
PUT /categorias/{id}
Content-Type: application/json

{
  "nome": "Eletrônicos Atualizado"
}
```

**Response: 200 OK**

```json
{
  "id": 1,
  "nome": "Eletrônicos Atualizado"
}
```

### Deletar Categoria

```http
DELETE /categorias/{id}
```

**Response: 204 No Content**

### Verificar Existência de Categoria

```http
GET /categorias/{id}/existe
```

**Response: 200 OK**

```json
true
```

## 🧪 Testes

Para executar os testes unitários:

```bash
./mvnw test
```

Ou usando Maven:

```bash
mvn test
```

## 📄 Estrutura do Projeto

```
API-de-Categorias/
├── src/
│   ├── main/
│   │   ├── java/com/API_de_Categorias/
│   │   │   ├── controller/     # Controladores REST
│   │   │   ├── dto/            # Data Transfer Objects
│   │   │   ├── entity/         # Entidades JPA
│   │   │   ├── exception/      # Tratamento de exceções
│   │   │   ├── repository/     # Repositórios JPA
│   │   │   └── service/        # Lógica de negócio
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/API_de_Categorias/
│           └── service/        # Testes unitários
├── postman_collection.json
├── pom.xml
└── README.md
```

## 🔍 Validações

- **nome**: Obrigatório, entre 3 e 100 caracteres
- **nome**: Deve ser único (case-insensitive)

## ⚠️ Tratamento de Erros

A API retorna códigos de status HTTP apropriados:

- **201 Created**: Categoria criada com sucesso
- **200 OK**: Operação realizada com sucesso
- **204 No Content**: Categoria deletada com sucesso
- **400 Bad Request**: Dados inválidos
- **404 Not Found**: Categoria não encontrada
- **409 Conflict**: Nome já cadastrado
- **500 Internal Server Error**: Erro interno no servidor

## 📝 Coleção Postman

Uma coleção Postman está disponível no arquivo `postman_collection.json` para facilitar os testes dos endpoints.

## 🤝 Contribuindo

Contribuições são bem-vindas! Por favor:

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/MinhaFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

## 👤 Autor

Flavio Alexandre Ribeiro
Desenvolvido como parte do projeto de API de Categorias.

## 📧 Contato

GitHub: https://github.com/FlavioAlexandreRibeiro
LinkedIn: https://www.linkedin.com/in/flavioalexandreribeiro1986/?isSelfProfile=true

Para dúvidas ou sugestões, abra uma issue no repositório.

# Incubadora de Empresas – Back-end

> Trabalho Final — Spring Boot, Spring Security e JWT  
> Disciplina: Desenvolvimento de Software WEB  
> Prof. Alexandre Cláudio de Almeida

---

## Sobre o Projeto

API REST desenvolvida em **Java com Spring Boot** para o sistema de gestão da Incubadora de Empresas da PUC Goiás (DAP).

O back-end é responsável por:
- Autenticação segura com **JWT (JSON Web Token)**
- Persistência dos dados no **MySQL**
- Disponibilização dos dados para o front-end via **API REST**

---

## Justificativa da Arquitetura

A divisão em camadas seguiu o padrão **MVC** com separação de responsabilidades.

| Camada | O que faz |
|--------|-----------|
| `model/` | Define as entidades mapeadas para o banco de dados (`Startup`, `Usuario`). |
| `repository/` | Interface de acesso ao banco. O Spring Data JPA fornece os métodos automaticamente. |
| `service/` | Contém as regras de negócio. É aqui que ficam validações e lógica da aplicação. |
| `controller/` | Recebe as requisições HTTP e chama os services. Não contém lógica de negócio. |
| `dto/` | Objetos de transferência de dados usados nas requisições e respostas da API. |
| `security/` | Configuração do Spring Security, geração e validação do JWT, e filtro de autenticação. |
| `config/` | Inicialização de dados (criação automática do usuário admin na primeira execução). |

### Fluxo de uma requisição autenticada

```
Front-end
└── Requisição HTTP com header Authorization: Bearer {token}
    └── JwtFilter (intercepta e valida o token)
        └── SecurityConfig (verifica se a rota precisa de autenticação)
            └── Controller (recebe a requisição validada)
                └── Service (executa a regra de negócio)
                    └── Repository (acessa o banco de dados MySQL)
                        └── Resposta JSON para o front-end
```

### Por que JWT e não sessão?

O front-end (React) e o back-end (Spring) rodam em servidores separados. O JWT permite que o back-end valide o usuário sem precisar guardar sessão no servidor — cada requisição carrega o token e o back-end verifica sua assinatura. Isso torna a API **stateless** e mais escalável.

### Por que Spring Data JPA?

Com o JPA, as tabelas do banco são criadas automaticamente a partir das entidades Java, sem precisar escrever SQL manualmente. O Hibernate gerencia o mapeamento objeto-relacional.

---

## Estrutura de Pastas

```
src/main/java/br/edu/pucgoias/incubadorabackend/
├── config/
│   └── DataInitializer.java       # Cria o usuário admin na primeira execução
├── controller/
│   ├── AuthController.java        # Endpoints de login e cadastro (públicos)
│   └── StartupController.java     # Endpoints de gestão das startups (protegidos)
├── dto/
│   ├── LoginRequest.java          # Dados recebidos no login (email + senha)
│   └── LoginResponse.java         # Dados retornados no login (token + nome)
├── model/
│   ├── Startup.java               # Entidade mapeada para a tabela 'startups'
│   └── Usuario.java               # Entidade mapeada para a tabela 'usuarios'
├── repository/
│   ├── StartupRepository.java     # Acesso ao banco para startups
│   └── UsuarioRepository.java     # Acesso ao banco para usuários
├── security/
│   ├── JwtFilter.java             # Intercepta requisições e valida o token
│   ├── JwtUtil.java               # Gera e valida tokens JWT
│   └── SecurityConfig.java        # Define rotas públicas e protegidas + CORS
└── service/
    ├── AuthService.java           # Lógica de login e cadastro de usuários
    └── StartupService.java        # Lógica de negócio das startups
```

---

## Endpoints da API

### Autenticação (públicos — não precisam de token)

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/api/auth/login` | Realiza login e retorna token JWT |
| POST | `/api/auth/cadastrar` | Cadastra novo usuário |

### Startups (protegidos — requerem `Authorization: Bearer {token}`)

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/api/startups` | Lista todas as startups |
| POST | `/api/startups` | Cadastra nova startup |
| PUT | `/api/startups/{id}` | Atualiza dados de uma startup |
| DELETE | `/api/startups/{id}` | Remove uma startup |
| PATCH | `/api/startups/{id}/avancar` | Avança startup para o próximo ciclo |
| PATCH | `/api/startups/{id}/desclassificar` | Desclassifica uma startup |
| PATCH | `/api/startups/{id}/relatorio` | Registra relatório como enviado |

---

## Banco de Dados

As tabelas são criadas automaticamente pelo Hibernate ao iniciar a aplicação.

| Tabela | Campos principais |
|--------|-------------------|
| `usuarios` | id, nome, email (único), senha (BCrypt) |
| `startups` | id, nome, fundador, setor, ciclo, descricao, data_entrada, relatorio_enviado |

---

## Como rodar

### Pré-requisitos

- Java 17+
- Maven 3.9+
- MySQL 8.0+

### 1. Criar o banco de dados

```sql
CREATE DATABASE incubadora_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. Configurar as credenciais

Edite o arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.username=root
spring.datasource.password=SUA_SENHA
```

### 3. Executar

```bash
mvn spring-boot:run
```

O servidor inicia na porta **8080**.

Na primeira execução, um usuário admin é criado automaticamente:
- **Email:** `admin@incubadora.com`
- **Senha:** `admin123`

---

## Identificação

**Hemily Ramos**  
Análise e Desenvolvimento de Sistemas — Escola Politécnica e de Artes da PUC Goiás  
Desenvolvimento de Software WEB — Prof. Alexandre Cláudio de Almeida — Junho de 2026

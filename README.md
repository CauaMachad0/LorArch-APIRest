# LorArch – Aplicação Web & API REST com Spring Boot

## 📑 Descrição do Projeto
O **LorArch** é uma aplicação web com **Spring Boot** que permite **gerenciar o fluxo operacional de motos em um galpão**.  
Além da API REST, agora possui **interface web com Thymeleaf**, autenticação com **Spring Security** e versionamento de banco via **Flyway**.

---

## 🎯 Objetivos

- 📋 Organizar e registrar a entrada e saída de motos.  
- 🔍 Rastrear o status de cada moto (disponível, em manutenção, danificada, etc.).  
- 🛠️ Registrar ocorrências associadas a cada moto (ex.: manutenção, problemas ou uso).  
- 🗃️ Fornecer uma API REST robusta e também uma interface web amigável para operadores.

---

## 🏗️ Arquitetura

O projeto segue arquitetura em **camadas**:
- **Controller (REST e Web)** – recebe requisições HTTP (API em `/api/**` e páginas web em `/motos/**`).
- **Service** – regras de negócio e validações.
- **Repository** – persistência (Spring Data JPA).
- **DTO** – transferência de dados entre API e cliente.
- **Model** – entidades JPA.

---

## 🛠️ Tecnologias

- **Java 21**  
- **Spring Boot 3.2.x**  
  - Spring Web (REST + MVC)  
  - Spring Data JPA  
  - Spring Security (login, logout e controle de permissões)  
  - Spring Cache  
- **Thymeleaf** (páginas HTML com fragmentos)  
- **Flyway** (migração e versionamento do banco – 4 versões)  
- **H2 Database** (em memória para dev/teste)  
- **Bean Validation**  
- **Gradle**  
- **Postman** para testes de API

---

## ⚙️ Banco de Dados (H2 + Flyway)

- **Console H2:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)  
- Credenciais padrão: `jdbc:h2:mem:lorarch`, usuário `sa`, senha em branco.  
- Migrações automáticas: `src/main/resources/db/migration`  
  - V1__create_tables.sql  
  - V2__seed_initial_data.sql  
  - V3__security_seed_users.sql (cria usuários `admin` e `operador`)  
  - V4__indexes.sql

---

## 🚀 Como Executar

### Pré-requisitos
- JDK 21+
- Gradle (ou usar wrapper `./gradlew`)

### Passo a passo
bash:
git clone https://github.com/CauaMachad0/LorArch.git
cd LorArch
./gradlew bootRun   # ou gradlew.bat bootRun no Windows

A aplicação web estará em: http://localhost:8080

## 🔐 Login e Perfis

- Usuário Admin: admin / admin123 – acesso total (criar/editar/deletar).
- Usuário Operador: operador / oper123 – pode cadastrar e atualizar, mas não deletar.

## 🌐 Interface Web (Thymeleaf)
- /login – tela de autenticação.
- /motos – lista, criação, edição e exclusão de motos.
- /ocorrencias – gerenciamento de ocorrências.
  
- Fluxos Extras:
  - Enviar moto para manutenção (gera ocorrência e altera status).
  - Concluir manutenção e devolver ao uso.

## 📡 API REST

- Todos os endpoints REST agora estão em /api/**.
- Endpoints de Motos
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/motos` | Cria uma nova moto. |
| `GET` | `/api/motos` | Lista todas as motos. |
| `GET` | `/api/motos/{id}` | Busca uma moto específica por ID. |
| `PUT` | `/api/motos/{id}` | Atualiza os dados de uma moto existente. |
| `DELETE` | `/api/motos/{id}` | **(ADMIN)** Deleta uma moto do sistema. |

### Exemplo de JSON para Moto

json:
{
  "placa": "ABC1234",
  "modelo": "Honda Biz 125",
  "status": "DISPONIVEL",
  "setor": "Galpao Central"
}

-
- Endpoints de Ocorrências
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/motos` | Cria uma nova ocorrência. |
| `GET` | `/api/motos` | Lista todas as ocorrências. |
| `GET` | `/api/motos/{id}` | Busca uma ocorrência específica por ID. |
| `PUT` | `/api/motos/{id}` | Atualiza os dados de uma ocorrência. |
| `DELETE` | `/api/motos/{id}` | **(ADMIN)** Deleta uma ocorrência. |

### Exemplo de JSON para Moto

json:
{
  "tipo": "DANO",
  "descricao": "Risco na lateral esquerda",
  "data": "2024-05-20",
  "motoId": 1
}

## 🧱 Status da Moto (Enum)

| Valor | Significado |
| `NOVA` | Recém-cadastrada |
| `DISPONIVEL` | Disponível para uso | 
| `EM_USO` | Em operação |
| `EM_MANUTENCAO` | Em manutenção/oficina |
| `DANIFICADA` | Com dano pendente |
| `FALTANDO` | Ausente/não localizada |

## 💾 Cache

- Listagens de motos e ocorrências usam cache.
- Criação, edição ou exclusão invalida automaticamente o cache.

## 🧩 Melhorias Futuras

- Deploy em nuvem (AWS, Render, Railway etc.).
- Dashboard React/Angular para visualização.
- Integração com bancos externos (PostgreSQL, MySQL…).

## 👨‍💻 Autores

- Feito com 💙 por Cauã Marcelo Machado
- Colaboradores: Gabriel Lima e Marcos Ramalho

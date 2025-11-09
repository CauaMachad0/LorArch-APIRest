# 🏍️ LorArch – Aplicação Web & API REST com Spring Boot

## 📘 Descrição do Projeto
O **LorArch** é uma aplicação Web + API REST em **Spring Boot** para **gerenciar motos em um galpão**.
Inclui autenticação (Spring Security), interface web com Thymeleaf, cache e persistência em Oracle.

---

## 🎯 Objetivos
* Registrar **entrada/saída/manutenção** de motos
* Acompanhar **status da frota**
* Lançar **ocorrências** (manutenção, uso, diagnóstico etc.)
* Operar via **páginas web** e via **API REST**
* **Gerar ocorrência automaticamente** ao enviar moto danificada/indisponível para manutenção

---

## 🧱 Arquitetura

### Camadas
| Camada | Função |
|---|---|
| **Controller**| REST (`/api/**`) e Web/Thymeleaf (`/motos/**`, `/ocorrencias/**`) |
| **Service** | Regras de negócio e validações |
| **Repository**| Acesso a dados (Spring Data JPA) |
| **DTO** | Objetos de transferência (formulários e API) |
| **Model** | Entidades JPA |
| **Config** | Segurança, beans e configurações globais |
| **Exception** | Tratamento de exceções |
| **Resources** | Templates HTML, assets, favicon |

---

## ⚙️ Tecnologias
* **Java 21**
* **Spring Boot 3.2.x**
* **Spring Web (REST + MVC)**
* **Spring Data JPA**
* **Spring Security**
* **Spring Cache**
* **Thymeleaf** + **Bootstrap 5**
* **Oracle Database**
* **Bean Validation (Jakarta)**
* **Gradle**

---

## 🗄️ Banco de Dados (Oracle)

properties
spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521/ORCL
spring.datasource.username=RM558024
spring.datasource.password=270605
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=false
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.OracleDialect
spring.jpa.properties.hibernate.default_schema=RM558024

## Como Executar

### Pré-requisitos
* JDK 21+
* Gradle (ou use o wrapper)

### Passos
bash
git clone [https://github.com/CauaMachad0/LorArch.git](https://github.com/CauaMachad0/LorArch.git)
cd LorArch
./gradlew bootRun    # Windows: gradlew.bat bootRun

### Acesso
* **Web:** `http://localhost:8081`
* **API:** `http://localhost:8081/api/**`

### 🔐 Login
| Usuário | Senha | Perfil |
|---|---|---|
| admin | 1234 | Admin (CRUD completo) |

---

## 🧩 Interface Web (Thymeleaf)

### Rotas principais
| Rota | Descrição |
|---|---|
| `/` | Dashboard (Resumo da Frota) |
| `/login` | Tela de autenticação |
| `/register` | Registro de usuário |
| `/motos` | Listagem/ações de motos |
| `/ocorrencias` | Listagem/ações de ocorrências |

### Fluxos extras de motos
* `POST /motos/{id}/manutencao` → envia para manutenção (gera ocorrência automática)
* `POST /motos/{id}/concluir-manutencao` → retorna moto ao status DISPONIVEL

---

## 🧠 API REST

### Convenções
* **Base:** `/api/**`
* **Content-Type:** `application/json`
* **Validações:** Bean Validation → `400` em payload inválido
* **Erros comuns:** `404` (não encontrado), `422` (regra de negócio)

### 🏍️ Motos (REST)
| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/motos` | Cria moto |
| `GET` | `/api/motos` | Lista motos |
| `GET` | `/api/motos/{id}` | Busca por ID |
| `PUT` | `/api/motos/{id}` | Atualiza |
| `DELETE` | `/api/motos/{id}` | Remove (admin) |

> **Exemplo – MotoDTO**
json
{
  "placa": "ABC1D23",
  "modelo": "Honda Biz 125",
  "status": "DISPONIVEL",
  "setor": "Galpão Central"
}

## ⚙️ Ocorrências (REST)

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/ocorrencias` | Cria ocorrência |
| `GET` | `/api/ocorrencias` | Lista ocorrências |
| `GET` | `/api/ocorrencias/{id}` | Busca por ID |
| `PUT` | `/api/ocorrencias/{id}` | Atualiza |
| `DELETE` | `/api/ocorrencias/{id}` | Remove (admin) |

> **Exemplo – OcorrenciaDTO**
json
{
  "tipo": "MANUTENCAO",
  "descricao": "Troca de kit relação",
  "data": "2025-10-01",
  "custo": 120.50,
  "motoId": 1,
  "setorId": 2
}

> **Validações do DTO**
* **tipo** (obrigatório) – `ENTRADA`, `SAIDA`, `MANUTENCAO`, `DIAGNOSTICO`
* **descricao** – até 200 chars
* **data** (obrigatório) – `YYYY-MM-DD`
* **custo** – $\ge 0.00$
* **motoId**, **setorId** – obrigatórios

---

## 🏷️ Status da Moto (Enum)

| Valor | Significado |
|---|---|
| `NOVA` | Recém-cadastrada |
| `DISPONIVEL` | Disponível para uso |
| `EM_USO` | Em operação |
| `EM_MANUTENCAO` | Em manutenção/oficina |
| `DANIFICADA` | Com dano pendente |
| `INDISPONIVEL` | Fora de operação |
| `FALTANDO` | Não localizada |

---

## ⚡ Cache
* **Caches:** `motos`, `ocorrencias`
* **Evicções automáticas:** criar/atualizar/excluir invalidam as listagens

---

## 💅 UI/UX
* **Paleta:** `#121310` (fundo) e `#f0f2f5` (conteúdo)
* Layout responsivo (Bootstrap 5)
* **Favicon:** `src/main/resources/static/assets/favicon-16.png`

html
<link rel="icon" type="image/png" sizes="16x16" th:href="@{/assets/favicon-16.png}" />

---


## 🛠️ Notas de Implementação
* **Segurança:** Spring Security + BCrypt
* **Login customizado:** `/login` → redireciona para `/`
* **CSRF:** habilitado (forms Thymeleaf incluem token)
* **Ocorrência automática:** ao enviar para manutenção via painel Web
* **Tratamento global de erros:** `GlobalExceptionHandler`

---

## 🚧 Melhorias Futuras
* Deploy (AWS/Render/Railway)
* Dashboard SPA (React/Vue)
* Integração IoT (LoRa/RFID)
* Notificações em tempo real (WebSocket)

---

## 👨‍💻 Autores
Feito com 💙 por **Cauã Marcelo Machado**
Colaboradores: **Gabriel Lima** e **Marcos Ramalho**

---

## 🏁 Licença
Uso acadêmico e educacional.

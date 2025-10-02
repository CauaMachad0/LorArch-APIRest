# LorArch – Aplicação Web & API REST com Spring Boot

## Descrição do Projeto

O **LorArch** é uma aplicação web em Spring Boot para gerenciar o fluxo operacional de motos em um galpão. O projeto oferece API REST e interface web (Thymeleaf), com autenticação via Spring Security e Oracle DB para desenvolvimento.

## Objetivos

- Registrar entrada/saída de motos
-  Acompanhar status (disponível, manutenção, danificada, etc.)
- Lançar ocorrências (manutenção, diagnóstico, uso, etc.)
- Disponibilizar API REST e páginas web para operação

## Arquitetura

### Controllers
- **REST**: `/api/**`
- **Web (Thymeleaf)**: `/motos/**` e `/ocorrencias/**`

### Camadas
- **Service**: regras de negócio e validações
- **Repository**: persistência (Spring Data JPA)
- **DTO**: objetos de entrada (API/Form)
- **Model**: entidades JPA
- **Config**: Configruações do projeto
- **Excepition**: Trabalho das exceções
- **Resources**: Parte WEB do projeto

## Tecnologias

- Java 21
- Spring Boot 3.2.x
- Spring Web (REST + MVC)
- Spring Data JPA
- Spring Security
- Spring Cache
- Thymeleaf (views simples, sem layout/fragmentos globais)
- Oracle Database
- Bean Validation
- Gradle

## Banco de Dados (ORACLE)

- spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521/ORCL
- spring.datasource.username=RM554611
- spring.datasource.password=14102024
- spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

> **Observação**: use a propriedade `spring.jpa.hibernate.ddl-auto` conforme sua necessidade (`update`, `create-drop`, etc.).

## Como Executar

### Pré-requisitos

- JDK 21+
- Gradle (ou usar o wrapper)

### Passos
git clone https://github.com/CauaMachad0/LorArch.git
cd LorArch
./gradlew bootRun   # Windows: gradlew.bat bootRun

## Aplicação Web
**URL:** `http://localhost:8081` *(ou `8080`, conforme configuração)*

---

## Login

- **Admin:** `admin / 1234` – acesso total (criar/editar/deletar)  

---

## Interface Web (Thymeleaf)

- `GET /login` – autenticação  
- `GET /motos` – listagem  
- `GET /ocorrencias` – listagem  
- `GET /motos/novo`, `POST /motos` – criar  
- `GET /motos/{id}/editar`, `POST /motos/{id}` – editar/atualizar  
- `POST /motos/{id}/excluir` – excluir  
- `GET /ocorrencias/novo`, `POST /ocorrencias` – criar  
- `GET /ocorrencias/{id}/editar`, `POST /ocorrencias/{id}` – editar/atualizar  
- `POST /ocorrencias/{id}/excluir` – excluir  

### Fluxos extras (motos)

- `POST /motos/{id}/manutencao` – envia para manutenção *(gera ocorrência + altera status)*  
- `POST /motos/{id}/concluir-manutencao` – conclui manutenção e devolve ao uso

---

## Observações

- **Views simplificadas:** sem `layout.html`/fragmentos globais para evitar erros de template.  
- **Sem LazyInitialization nas listas:** repositório de Ocorrências usa `@EntityGraph` para carregar `moto` e `setor` na listagem, permitindo uso de `oc.moto.placa` e `oc.setor.nome` nas páginas.

---

## API REST

### Convenções
- **Base path:** `/api/**`  
- **Content-Type:** `application/json`  
- **Validações:** Bean Validation → responde **400** em payload inválido

### Motos

| Método | Endpoint            | Descrição                  |
|:------:|---------------------|----------------------------|
| POST   | `/api/motos`        | Cria uma nova moto         |
| GET    | `/api/motos`        | Lista todas as motos       |
| GET    | `/api/motos/{id}`   | Busca moto por ID          |
| PUT    | `/api/motos/{id}`   | Atualiza dados da moto     |
| DELETE | `/api/motos/{id}`   | **(ADMIN)** Remove a moto  |

**Exemplo de JSON (MotoDTO)**
json
{
  "placa": "ABC1D23",
  "modelo": "Honda Biz 125",
  "status": "DISPONIVEL",
  "setor": "Galpao Central"
}

## Ocorrências

| Método | Endpoint                  | Descrição                     |
|:------:|---------------------------|-------------------------------|
| POST   | `/api/ocorrencias`       | Cria uma ocorrência           |
| GET    | `/api/ocorrencias`       | Lista todas as ocorrências    |
| GET    | `/api/ocorrencias/{id}`  | Busca ocorrência por ID       |
| PUT    | `/api/ocorrencias/{id}`  | Atualiza uma ocorrência       |
| DELETE | `/api/ocorrencias/{id}`  | **(ADMIN)** Remove uma ocorrência |

**Exemplo de JSON (OcorrenciaDTO)**
json
{
  "tipo": "Manutencao",
  "descricao": "Troca de kit relação",
  "data": "2025-10-01",
  "custo": 120.50,
  "motoId": 1,
  "setorId": 2
}

## Validações (OcorrenciaDTO)

- `tipo` (**obrigatório**) – valores aceitos: **Entrada**, **Saida**, **Manutencao**, **Diagnostico**
- `descricao` – máximo **200** caracteres
- `data` (**obrigatório**) – formato **YYYY-MM-DD**
- `custo` (**obrigatório**) – **>= 0.00**
- `motoId` (**obrigatório**)
- `setorId` (**obrigatório**)

---

## Status da Moto (Enum)

| Valor           | Significado            |
|-----------------|------------------------|
| `NOVA`          | Recém-cadastrada       |
| `DISPONIVEL`    | Disponível para uso    |
| `EM_USO`        | Em operação            |
| `EM_MANUTENCAO` | Em manutenção/oficina  |
| `DANIFICADA`    | Com dano pendente      |
| `FALTANDO`      | Ausente/não localizada |

---

## Cache

- **Caches:** `motos` e `ocorrencias`
- **Evicções automáticas:** criar/atualizar/excluir **invalidam** as listagens

---

## Notas de Implementação

- **Thymeleaf (Web):** formulários com token **CSRF** quando habilitado  
- **Ocorrências (Repository):** `@EntityGraph(attributePaths = {"moto","setor"})` nas consultas usadas nas views  
- **Erros:** mensagens amigáveis na Web; **HTTP 400/404/422** na API conforme validações e recursos

---

## Melhorias Futuras

- Deploy em nuvem (AWS/Render/Railway)
- Dashboard SPA (React/Angular)
- Banco externo (PostgreSQL/MySQL)

---

## Autores

- Feito com 💙 por **Cauã Marcelo Machado**  
- Colaboradores: **Gabriel Lima** e **Marcos Ramalho**




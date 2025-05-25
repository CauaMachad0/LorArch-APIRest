# LorArch – API REST com Spring Boot

## 📑 Descrição do Projeto

O **LorArch** é uma API REST desenvolvida com **Spring Boot** que tem como objetivo **gerenciar o fluxo operacional de motos dentro de um galpão**. Essa API permite acompanhar o status das motos, registrar ocorrências (como manutenções, danos ou atualizações de uso) e manter um histórico organizado e acessível dessas informações.

O projeto simula uma operação comum em empresas que trabalham com locação, entrega ou manutenção de motos, como galpões de logística ou serviços de aluguel.

---

## 🎯 Objetivos

- 📋 Organizar e registrar a entrada e saída de motos.
- 🔍 Permitir rastrear o status de cada moto (disponível, em manutenção, danificada, etc.).
- 🛠️ Registrar ocorrências específicas associadas a uma moto (ex.: manutenção, problemas ou uso).
- 🗃️ Fornecer uma API REST robusta para integração com outros sistemas, apps mobile ou dashboards.

---

## 🏗️ Arquitetura do Projeto

O projeto segue uma arquitetura em **camadas**, utilizando boas práticas como:

- **Controller:** Camada responsável por receber requisições HTTP e enviar respostas.
- **Service:** Camada onde ficam as regras de negócio e a lógica da aplicação.
- **Repository:** Camada de persistência de dados (banco de dados).
- **DTO:** Objetos de transferência de dados entre a API e o cliente.
- **Model:** Representação das entidades do banco de dados.

---

## 🛠️ Tecnologias e Ferramentas

- **Java 17**
- **Spring Boot 3**
- **Spring Web**
- **Spring Data JPA**
- **Spring Cache (Cache de consultas)**
- **H2 Database (Banco de dados em memória para testes)**
- **Bean Validation (validação dos dados)**
- **Gradle (Gerenciamento de dependências e build)**
- **Postman (Testes dos endpoints)**

---

## ⚙️ Configuração do Banco de Dados (H2)

O projeto utiliza **H2 Database**, um banco de dados em memória que não requer instalação. Ele é ideal para desenvolvimento e testes rápidos.

Acesse o console H2 em: http://localhost:8080/h2-console

---

## 🏁 Como executar o projeto

### ✅ Pré-requisitos

- Java 17 instalado
- Gradle instalado ou usar uma IDE como IntelliJ, VSCode ou Eclipse com suporte a Gradle

### 🚀 Passo a passo para rodar:

1. Clone o repositório:
git clone https://github.com/CauaMachad0/LorArch.git

2. Acesse a pasta do projeto:
cd LorArch

3. Execute a aplicação

4. A API estará disponível em:
http://localhost:8080

## 🌐 Endpoints da API

### 🔗 /motos (Gestão de Motos)
 * | Método | Endpoint    | Descrição               |
 * | ------ | ----------- | ----------------------- |
 * | POST   | /motos      | Criar uma nova moto     |
 * | GET    | /motos      | Listar todas as motos   |
 * | GET    | /motos/{id} | Buscar moto por ID      |
 * | PUT    | /motos/{id} | Atualizar dados da moto |
 * | DELETE | /motos/{id} | Deletar moto            |
*/

### Exemplo de JSON para cadastro de moto:
- {
  - "placa": "ABC1234",
  - "modelo": "Honda Biz 125",
  - "status": "DISPONIVEL",
  - "setor": "Galpao Central"
- }

---

### 🔗 /ocorrencias (Gestão de Ocorrências)

 * | Método | Endpoint          | Descrição               |
 * | ------ | ------------------| ----------------------- |
 * | POST   | /ocorrencias      | Criar uma nova moto     |
 * | GET    | /ocorrencias      | Listar todas as motos   |
 * | GET    | /ocorrencias/{id} | Buscar moto por ID      |
 * | PUT    | /ocorrencias/{id} | Atualizar dados da moto |
 * | DELETE | /ocorrencias/{id} | Deletar moto            |
 */

### Exemplo de JSON para cadastro de ocorrência:
- {
  - "tipo": "DANO",
  - "descricao": "Risco na lateral esquerda",
  - "data": "2024-05-20",
  - "motoId": 1
- }

---

### Status da Moto (Enum)

Os status possíveis para as motos são:

- **DISPONIVEL**
- **EM_USO**
- **EM_MANUTENCAO**
- **DANIFICADA**
- **FALTANDO**
- **NOVA**

---

## Testando com Postman

### ✅ Passo a passo:

1. Abra o Postman.
2. Crie uma nova configuração
3. Configure os métodos(GET, POST, PUT, DELETE) e a URL:
http://localhost:8080/{endpoint}
4. Se for POST ou PUT, selecione a aba Body, escolha raw → JSON, e insira o JSON de exemplo.
5. Clique em Send para enviar a requisição e visualizar a resposta.

---

### 💾 Cache Implementado
- A listagem de motos (/motos) e ocorrências (/ocorrencias) possui cache para melhorar performance.
- Sempre que uma moto ou ocorrência é criada, atualizada ou deletada, o cache é automaticamente atualizado.

---

### 🧠 Melhorias Futuras
- Implementar autenticação e autorização com Spring Security.
- Deploy na nuvem (Render, AWS, Railway ou Heroku).
- Documentação da API com Swagger/OpenAPI.
- Integração com banco de dados externo (MySQL, PostgreSQL, Oracle).
- Dashboard para visualização dos dados em frontend React ou Angular.

---

## 👨‍💻 Autor
### Feito com 💙 por Cauã Marcelo Machado
### Colaboradores: Gabriel Lima e Marcos Ramalho

---

### ⭐ Se te ajudou, deixa uma estrela ⭐ no repositório!

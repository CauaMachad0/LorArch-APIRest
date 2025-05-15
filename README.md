# 🏍️ SmartMoto – Sistema Inteligente de Gerenciamento de Motos em Galpões

Projeto Integrador FIAP — Challenge Tech FIAP 2025  
📚 Disciplinas: Java | C# | Cloud | Mobile | Banco de Dados | IoT | QA

---

## 📌 Descrição do Projeto

O **SmartMoto** é um sistema inteligente e modular para **localizar, monitorar e gerenciar motocicletas dentro de armazéns e galpões** em tempo real.  
Cada moto possui um identificador (LoRa ou RFID), permitindo sua rastreabilidade, controle de status e registro de ocorrências como defeitos, manutenções e movimentações.

> Este projeto foi desenvolvido por uma equipe multidisciplinar com foco em escalabilidade, integração, automação e monitoramento na nuvem.

---

## 🚀 Funcionalidades Principais

- 📍 Localização em tempo real das motos por setor e unidade
- ⚠️ Registro de defeitos como "motor", "roubo", "avaria"
- 🔄 Atualização automática do status: `Ativa`, `Manutenção`, `Roubada`
- 🛠️ Registro de manutenções (data, descrição e custo)
- 🔁 Controle de movimentações entre setores
- 📊 Geração de relatórios (motos roubadas, manutenções, localização)

---

## 🧱 Arquitetura do Projeto

### 📁 Backend Java (Spring Boot)

- **API REST** com endpoints para gerenciamento de:
  - Motos
  - Setores e Unidades
  - Defeitos
  - Manutenções
  - Movimentações
- **Camadas:**
    src/
    ├── controller/ # Endpoints da API
    ├── service/ # Regras de negócio
    ├── repository/ # Repositórios (Spring Data JPA)
    ├── model/ # Entidades (JPA)
    ├── dto/ # Objetos de transferência de dados
    └── application.properties # Configurações
  

### 💻 Módulo em C#

- Integração com controle de setores e movimentações via .NET  
- Comunicação REST entre módulos Java ↔ C#

### 📲 Mobile (Kotlin ou Flutter)

- Aplicativo para operadores consultarem e atualizarem status das motos em campo

### ☁️ Cloud (Microsoft Azure)

- Deploy do backend com Docker e Azure App Service  
- Banco de dados PostgreSQL/Oracle hospedado na nuvem  
- Monitoramento com Azure Monitor  
- CI/CD com GitHub Actions  

### 🌐 IoT (LoRa ou RFID)

- Dispositivos de rastreamento instalados nas motos  
- Comunicação via LoRa + MQTT para localização precisa  
- Gateway IoT conectado ao backend  

### 🧪 Qualidade (QA)

- Testes automatizados com:
  - JUnit (backend Java)
  - Postman (testes de API)
  - Jest (mobile)
- Análise de código com SonarQube

---

## 📊 Banco de Dados

- Modelo relacional com tabelas normalizadas:
  - `MOTO`
  - `DEFEITO`
  - `SETOR`
  - `UNIDADE`
  - `MANUTENCAO`
  - `MOVIMENTACAO`
- Inclui `DATA_ATUALIZACAO` para rastreabilidade de alterações

---

## 🔐 Segurança

- Autenticação com JWT (JSON Web Tokens)  
- Roles para perfis: `admin`, `operador`, `suporte`  
- Controle de acesso por endpoint

---

## 📈 Relatórios e Métricas

- Dashboard com indicadores:
  - Total de motos ativas/inativas
  - Motos em manutenção por setor
  - Histórico de defeitos e custos
- Exportação para PDF ou integração com Power BI

---

## 🛠️ Como Rodar Localmente

### Pré-requisitos

- Java 17+  
- PostgreSQL ou Oracle  
- Docker  
- Node.js (para o mobile)  
- Git  

### Passos

# Clone o repositório
git clone https://github.com/seuusuario/smartmoto.git

# Acesse a pasta
cd smartmoto

# Rode o backend com Maven ou Gradle
./mvnw spring-boot:run

# Ou execute o docker-compose
docker-compose up --build

| Nome                 | Responsável por            | Curso/Disciplina |
| -------------------- | -------------------------- | ---------------- |
| Cauã Marcelo Machado | Backend Java + DevOps      | Java + Cloud     |
| Nome do integrante 2 | Mobile + IoT               | Mobile + IoT     |
| Nome do integrante 3 | Banco de Dados + C#        | C# + BD          |
| Nome do integrante 4 | QA + Testes + Documentação | QA + DevOps      |

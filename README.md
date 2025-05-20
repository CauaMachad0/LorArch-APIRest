# 🏍️ SmartMoto – Sistema Inteligente de Gerenciamento de Motos em Galpões

Projeto Integrador FIAP — Challenge Tech FIAP 2025  
📚 Disciplina: Java

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
    -src/
    - ├── controller/ # Endpoints da API
    - ├── service/ # Regras de negócio
    - ├── repository/ # Repositórios (Spring Data JPA)
    - ├── model/ # Entidades (JPA)
    - ├── dto/ # Objetos de transferência de dados
    - └── application.properties # Configurações
  
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

## 🛠️ Como Rodar Localmente

### Pré-requisitos

- Java 17+  
- Oracle  
- Git  

### Passos

# Clone o repositório
git clone [https://github.com/seuusuario/smartmoto.git](https://github.com/CauaMachad0/LorArch.git)

# Rode o backend com Maven ou Gradle
./mvnw spring-boot:run


| Nome                 | Responsável por            | Curso            |
| -------------------- | -------------------------- | ---------------- |
| Cauã Marcelo Machado | Backend Java + QA          | ADS - FIAP       |
| Gabriel Lima Silva   | Mobile + C#                | ADS - FIAP       |
| Marcos Ramalho       | Banco de Dados + Cloud     | ADS - FIAP       |


# 📚 LiterAlura - Catálogo de Livros Interativo

![Java](https://img.shields.io/badge/Java-25-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.13-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED)

Bem-vindo ao **LiterAlura**! Este é uma aplicação backend desenvolvida em Java 25 com Spring Boot que funciona como um catálogo de livros interativo via console. O sistema consome a API pública do **Gutendex** (Projeto Gutenberg), processa os dados e os persiste em um banco de dados relacional (PostgreSQL) para consultas futuras.

## 🧠 Arquitetura e Fluxo de Dados

### 1. Visão Geral do Sistema
O diagrama abaixo mostra como os componentes interagem entre si dentro do ambiente Docker.

```mermaid
graph TD
    User((Usuário))
    subgraph "Ambiente Docker"
        App["Container Aplicação<br/>(Spring Boot)"]
        DB[("Container PostgreSQL")]
    end
    API[☁️ API Gutendex]

    User -- "Interage via Console" --> App
    App -- "Busca Livros (HTTP)" --> API
    API -- "Retorna JSON" --> App
    App -- "Persiste/Consulta Dados" --> DB
```

### 2. Fluxo de Busca Inteligente (Algoritmo)
Implementamos uma lógica em camadas para garantir que o livro correto seja encontrado, utilizando Regex com barreiras de palavra para evitar correspondências parciais incorretas.

```mermaid
flowchart TD
    Start([Início da Busca]) --> Input[/Usuário digita Título/]
    Input --> Request[Requisição à API Gutendex]
    Request --> Response{Encontrou resultados?}
    
    Response -- Não --> NotFound([Retorna: Livro não encontrado])
    Response -- Sim --> Filter1{Match Exato?}
    
    Filter1 -- Sim --> Found([✅ Livro Encontrado])
    Filter1 -- Não --> Filter2{Contém Frase Completa?}
    
    Filter2 -- Sim --> Found
    Filter2 -- Não --> Filter3{"Match Palavras-Chave<br/>(Regex com barreiras)"}
    
    Filter3 -- Sim --> Found
    Filter3 -- Não --> NotFound
    
    Found --> CheckDB{Autor existe no BD?}
    CheckDB -- Sim --> LinkAutor[Associa Autor Existente]
    CheckDB -- Não --> SaveAutor[Salva Novo Autor]
    
    LinkAutor --> SaveBook[Salva Livro no BD]
    SaveAutor --> SaveBook
    SaveBook --> Show[Exibe Detalhes no Console]
```

---

## 🚀 Funcionalidades

1.  **🔍 Buscar livro pelo título:** Conecta-se à API e utiliza o algoritmo de busca inteligente.
2.  **📚 Listar livros registrados:** Exibe todos os livros salvos localmente.
3.  **✍️ Listar autores registrados:** Mostra autores e seus livros.
4.  **📅 Listar autores vivos em determinado ano:** Filtro histórico de autores.
5.  **🌐 Listar livros em determinado idioma:** Filtro por código de idioma (EN, PT, etc.).

---

## 🛠️ Tecnologias Utilizadas

*   **Java 25 LTS:** Versão mais recente e performática da linguagem.
*   **Spring Boot 3.4.13:** Framework atualizado para produtividade.
*   **Spring Data JPA:** Persistência de dados robusta.
*   **PostgreSQL 16:** Banco de dados relacional moderno.
*   **Docker & Docker Compose:** Containerização completa do ambiente.
*   **JUnit 5 & Mockito:** Suíte de testes automatizados de alta fidelidade.

---

## 🧪 Testes Automatizados Robustos

O projeto utiliza uma suíte de testes profissional para garantir a confiabilidade:

*   **Testes de Repositório (`DataJpaTest`):** Valida queries complexas e integridade do banco de dados (PostgreSQL 16).
*   **Testes Unitários de Serviço (`Mockito`):** Valida o algoritmo de busca e filtros de forma isolada e rápida.

Para rodar os testes:
```bash
./scripts/test.sh
```

---

## 🐳 Como Executar o Projeto

1.  **Reconstrução Completa (Primeira vez ou mudanças no código):**
    ```bash
    ./scripts/rebuild.sh
    ```
2.  **Inicialização Simples (Apenas para rodar):**
    ```bash
    ./scripts/start.sh
    ```

Ambos os scripts iniciam a aplicação em modo interativo direto no seu terminal.

---

## 👨‍💻 Autor

José Anderson da Silva Costa

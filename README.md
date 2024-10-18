# Projeto Cooperativa

## Descrição

Solução backend para gerenciar essas sessões de votação.

## Instruções para Executar o Banco de Dados

Para rodar o projeto é necessário ter o Docker e Docker Compose instalados. Após clonar o projeto, acesse a pasta raiz
do projeto e execute o comando abaixo para subir o banco de dados Postgres.

```bash
docker compose -f stack.yml up
```
Obs: No windows, o comando pode ser diferente, geralmente é `docker-compose up`.

## Conceitos aplicados

- DDD
- Hexagonal Architecture
- Design Patterns

## Entidades
    - Pauta como "Agenda" se refere à lista de tópicos ou demandas.
    - Sessão como "Voting session" processo de votação em um período específico.
    - Voto como "Vote".
    - Associado como "Member", uma pessoa com direitos dentro da cooperativa.

## Tecnologias

- Java 17
- Spring Boot
- Postgres
- Docker Compose
- Junit
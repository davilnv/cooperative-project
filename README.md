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

## Tecnologias

- Java 17
- Spring Boot
- Postgres
- Docker Compose
- Junit
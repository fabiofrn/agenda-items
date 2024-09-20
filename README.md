# Nome do Projeto

Projeto para cadastro e votação de pautas.

## Tabela de Conteúdos

1. [Sobre](#sobre)
2. [Como Começar](#como-começar)
3. [Pre-requisitos](#pre-requisitos)
4. [Funcionamento](#funcionamento)
5. [Variáveis](#variaveis)

## Sobre

Este projeto tem como objetivo cadastrar pautas, e possibilitar o voto por parte dos associados nas pautas cadastradas

## Como Começar

Ao baixar o projeto, favor rodar o build pelo maven para geração do jar, a seguir, é possivel executar o docker compose atraves do comando "docker-compose up --build" 
onde será criado o container da base e a geração do container da aplicação, do contrário pode rodar a aplicaçao localmente conectado a uma base MYSQL.
O projeto tem documentação atraves do swagger que pode ser acessado via {host}/{context}/swagger-ui/index.html ex.: http://localhost:8080/sicred/swagger-ui/index.html

### Pré-requisitos

Postman para executar as requisiçoes, Docker para executar via docker-compose ou jre 17/jdk17 para executar diretamente pelo host/IDE

### Funcionamento
É posível cadastrar a pauta, depois é necessário abri la, informando o tempo pelo qual ela poderá ser votada, se nao for informado o tempo é de um minuto.
Para votar é necessário usar um cpf valido sem formataçao em formato de String

O resultado da votação so pode ser acessado apos o fechamento da sessão.

### Variáveis
Para executar localmente, favor setar as variaveis abaixo
ENV_CONTEXT_PATH=/sicred
ENV_DATASOURCE.PASSWORD=root
ENV_DATASOURCE.URL=jdbc:mysql://localhost:3306/agenda-items
ENV_DATASOURCE.USER=root

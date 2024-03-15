# Testes Automatizados de API

## Introdução

Este repositório contém uma suite de testes automatizados desenvolvida em Java utilizando a biblioteca RestAssured. Os testes visam validar os endpoints de uma aplicação por meio de requisições HTTP. Esta suite de testes foi desenvolvida por Lucas Di Blasi.

### Endpoints Atendidos
- GET /test
- GET /users
- POST /auth/login
- GET /auth/products
- POST /products/add
- GET /products
- GET /products/{id}

## Automação

A automação foi implementada utilizando Java e RestAssured, uma biblioteca popular para testes de API em Java. RestAssured fornece uma sintaxe simples e intuitiva para realizar requisições HTTP e validar as respostas recebidas.


## Casos de Teste

### GET /test

**Sucesso:**

**Título:** Teste bem-sucedido da API de teste <br>
**Objetivo:** Verificar se a API de teste está acessível e retorna o status esperado.<br>
**Pré-condição:** Nenhuma.<br>
**Resultado Esperado:** Receber um status de resposta **200 (OK)** com uma mensagem indicando que o teste foi bem-sucedido.<br>

**Falha:**

**Título:** Teste mal-sucedido da API de teste<br>
**Objetivo:** Verificar o comportamento quando a API de teste não está disponível.<br>
**Pré-condição:** API de teste não está funcionando.<br>
**Resultado Esperado:** Receber um erro de conexão ou um status de resposta indicando falha, como **404 (Not Found)** <br>

### GET /users

**Sucesso:**

**Título:** Obter lista de usuários com sucesso<br>
**Objetivo:** Verificar se a API de usuários retorna a lista de usuários corretamente.<br>
**Pré-condição:** Existem usuários cadastrados no sistema.<br>
**Resultado Esperado:** Receber um status de resposta **200 (OK)** com a lista de usuários.<br>

**Falha:**

**Título:** Falha ao obter lista de usuários<br>
**Objetivo:** Verificar o comportamento quando a API de usuários não pode recuperar a lista de usuários.<br>
**Pré-condição:** API de usuários está inacessível ou ocorreu um erro interno.<br>
**Resultado Esperado:** Receber um status de resposta indicando falha, como **404 (Not Found)**<br>

### POST /auth/login

**Sucesso:**

**Título:** Login bem-sucedido<br>
**Objetivo:** Verificar se é possível fazer login com credenciais válidas.<br>
**Pré-condição:** Credenciais de usuário válidas são fornecidas.<br>
**Resultado Esperado:** Receber um status de resposta **200 (OK)** com um token de autenticação válido.<br>

**Falha:**

**Título:** Falha no login devido a credenciais inválidas<br>
**Objetivo:** Verificar o comportamento quando as credenciais de login são inválidas.<br>
**Pré-condição:** Credenciais de usuário inválidas são fornecidas.<br>
**Resultado Esperado:** Receber um status de resposta indicando falha, como **400 (Bad Request)**.<br>

### GET /auth/products

**Sucesso:**

**Título:** Obter lista de produtos autenticado<br>
**Objetivo:** Verificar se é possível obter a lista de produtos autenticado.<br>
**Pré-condição:** O usuário está autenticado e tem permissão para acessar os produtos.<br>
**Resultado Esperado:** Receber um status de resposta **200 (OK)** com a lista de produtos.<br>

**Falha:**

**Título:** Falha ao obter lista de produtos devido a falta de autenticação<br>
**Objetivo:** Verificar o comportamento quando o usuário não está autenticado.<br>
**Pré-condição:** O usuário não está autenticado.<br>
**Resultado Esperado:** Receber um status de resposta indicando falha, como **401 (Unauthorized)**.<br>

### POST /products/add

**Sucesso:**

**Título:** Adicionar produto com sucesso<br>
**Objetivo:** Verificar se é possível adicionar um novo produto com dados válidos.<br>
**Pré-condição:** Dados válidos do produto são fornecidos.<br>
**Resultado Esperado:** Receber um status de resposta **200 (OK)** e confirmar que o produto foi adicionado corretamente.<br>

**Falha:**

**Título:** Falha ao adicionar produto devido a dados inválidos<br>
**Objetivo:** Verificar o comportamento quando os dados do produto são inválidos.<br>
**Pré-condição:** Dados inválidos do produto são fornecidos.<br>
**Resultado Esperado:** Receber um status de resposta indicando falha, como **400 (Bad Request)** <br>

### GET /products

**Sucesso:**

**Título:** Obter lista de produtos<br>
**Objetivo:** Verificar se é possível obter a lista de produtos.<br>
**Pré-condição:** Existem produtos cadastrados no sistema.<br>
**Resultado Esperado:** Receber um status de resposta **200 (OK)** com a lista de produtos.<br>


### GET /products/{id}

**Sucesso:**

**Título:** Obter detalhes do produto com sucesso<br>
**Objetivo:** Verificar se é possível obter os detalhes de um produto específico.<br>
**Pré-condição:** O ID do produto válido é fornecido.<br>
**Resultado Esperado:** Receber um status de resposta **200 (OK)** com os detalhes do produto correspondente ao ID fornecido.<br>

**Falha:**

**Título:** Falha ao obter detalhes do produto devido a ID inválido<br>
**Objetivo:** Verificar o comportamento quando o ID do produto fornecido é inválido.<br>
**Pré-condição:** O ID do produto fornecido não é válido.<br>
**Resultado Esperado:** Receber um status de resposta indicando falha, como **404 (Not Found)** ou **400 (Bad Request)**.<br>

## Relatório de Testes

Após a execução da suite de testes, um relatório detalhado é gerado utilizando o framework Allure. Este relatório fornece uma visão abrangente dos testes executados, incluindo estatísticas, logs, capturas de tela (se aplicável) e outras informações úteis para análise e acompanhamento dos resultados dos testes.

Este projeto está alocado dentro do GitLab, onde será possível acessar o código-fonte, os artefatos da automação e os relatórios gerados pelo Allure após a execução dos testes.

**Allure:**

O relatório gerado pelo Allure está incluído no artefact do GitLab, podendo ser baixado na máquina para a análise do relatório. Lembre-se de que é necessário configurar o Allure em seu ambiente para executar o relatório.


**Junit:**

Ao final da execução, junto com o relatório do Allure, também é gerado o relatório do JUnit, que será visível dentro da pipeline, na aba "tests".


### Pontos de Melhoria e atenção

Em alguns endpoints, é possível enviar payloads com campos vazios ou ausentes. Propositadamente, um dos testes foi configurado para falhar, demonstrando essa questão. Quando se espera um status de falha 400, e a resposta da API indica sucesso evidencia que a API possui ausência de validação de campos.

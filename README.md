# Sobre o Projeto

Ponto Inteligente é um sistema de ponto eletrônico para controle de horas trabalhadas, permitindo um gerenciamento eficiente de recursos humanos. O projeto tem como objetivo facilitar o registro de ponto dos funcionários, cálculos de horas trabalhadas, controle de férias e outros aspectos seguindo a CLT.

Endpoints da API

1. Consultar lançamentos de um funcionário

GET http://url_base/api/lancamentos/funcionario/{funcionarioId}?pag=0&ord=id&dir=DESC

Parâmetros:

funcionarioId (path variable): ID do funcionário.

pag (query param): Número da página (padrão: 0).

ord (query param): Campo de ordenação (padrão: id).

dir (query param): Direção da ordenação (ASC ou DESC).

Exemplo de Resposta:

{
"data": {
"content": [
{
"id": 2,
"data": "2025-03-04 20:30:12",
"tipo": "TERM_WORK",
"descricao": "Fim do expediente",
"localizacao": "-22.97556329136049, -47.17074849905703",
"funcionarioId": 2
},
{
"id": 1,
"data": "2025-03-04 14:25:12",
"tipo": "START_WORK",
"descricao": "Início do expediente",
"localizacao": "-22.97556329136049, -47.17074849905703",
"funcionarioId": 2
}
],
"totalElements": 2,
"totalPages": 1,
"size": 25,
"number": 0,
"first": true,
"last": true
},
"errors": []
}

2. Criar um novo lançamento

POST http://url_base/api/lancamentos/launch

Corpo da Requisição:

{
"data": "2025-03-05 14:30:12",
"tipo": "TERM_WORK",
"descricao": "Fim do expediente",
"localizacao": "-22.97556329136049, -47.17074849905703",
"funcionarioId": 3
}

Exemplo de Resposta:

{
"data": {
"id": 4,
"data": "2025-03-05 14:30:12",
"tipo": "TERM_WORK",
"descricao": "Fim do expediente",
"localizacao": "-22.97556329136049, -47.17074849905703",
"funcionarioId": 3
},
"errors": []
}

3. Consultar empresa por CNPJ

GET http://url_base/api/empresas/cnpj/{cnpj}

Parâmetros:

cnpj (path variable): CNPJ da empresa.

Exemplo de Resposta:

{
"data": {
"id": 1,
"razaoSocial": "Samuel IT hungry",
"cnpj": "82198127000121"
},
"errors": []
}

4. Cadastrar Pessoa Física (PF)

POST http://url_base/api/cadastra-pf

Corpo da Requisição:

{
"nome": "Vitoria Souza",
"email": "vitoria@gmail.com",
"senha": "vic123",
"cpf": "33212314095",
"cnpj": "92682401000197"
}

Exemplo de Resposta:

{
"data": {
"id": 4,
"nome": "Joao Antonio",
"email": "joao@gmail.com",
"senha": null,
"cpf": "31637825072",
"valorHora": null,
"qtdHorasTrabalhoDia": null,
"qtdHorasAlmoco": null,
"cnpj": "31637825072"
},
"errors": []
}

5. Cadastrar Pessoa Jurídica (PJ)

POST http://url_base/api/cadastra-pj

Corpo da Requisição:

{
"nome": "SSTec",
"email": "samucagm@rocketmail.com",
"senha": "admin123",
"razaoSocial": "Samuel Solucoes Tecnologica",
"cnpj": "92682401000197",
"cpf": "01776904028"
}

Exemplo de Resposta:

{
"data": {
"id": 5,
"nome": "SSTec2",
"email": "samucagm2@rocketmail.com",
"senha": null,
"razaoSocial": "Samuel Solucoes Tecnologica",
"cnpj": "38520294000155",
"cpf": "01239964064"
},
"errors": []
}

6. Resposta de erro

Em caso de erro, a API retorna:

{
"data": null,
"errors": [
"Existing Company",
"Existing CPF",
"Existing Email"
]
}

## Tecnologias Utilizadas

Java (Spring Boot)

Banco de Dados (MySQL/PostgreSQL)

Autenticação (JWT)

Versionamento (Git/GitHub)

CI/CD (Travis CI)

## Contato

Autor: Samuel Oliveira

Email: samucagm@rocketmail.com

LinkedIn: linkedin.com/in/samuel
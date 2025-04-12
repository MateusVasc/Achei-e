<div align="center">

# Achei-e

  <p align="center">
    Achados e Perdidos
    <br />
    <br />
    <a href="https://github.com/MateusVasc/Achei-e/issues">Reportar Bug</a>
    ·
    <a href="https://github.com/MateusVasc/Achei-e/issues">Solicitar Funcionalidade</a>
  </p>
</div>

## Sobre o Projeto

O Achei-e é uma plataforma online que oferece uma rede social dedicada à gestão de itens perdidos e encontrados dentro da universidade. Os usuários podem cadastrar-se, autenticar-se e interagir com a plataforma para registrar itens perdidos, visualizar itens encontrados, marcar itens como devolvidos e entrar em contato com outros usuários para facilitar a devolução dos objetos perdidos. Nesta repositório encontra-se todo o código destinado a API dessa plataforma, todo o código relacionado ao front-end da aplicação encontra-se no repositório https://github.com/JoaoLucasCordeiro/achei-e_client.

## Tecnologias Utilizadas

![Spring-Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F.svg?style=for-the-badge&logo=Spring-Boot&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Postgres](https://img.shields.io/badge/PostgreSQL-4169E1.svg?style=for-the-badge&logo=PostgreSQL&logoColor=white)

## Configuração do ambiente de desenvolvimento

<h3>Pré-requisitos: </h3>

**Instalação do [JDK 19](https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html) (Java SE Development Kit)**

**Instalação do [Intellij](https://www.jetbrains.com/pt-br/idea/download/?section=windows) ou [Eclipse](https://www.eclipse.org/downloads/)**

**Instalação do [PostgreSQL](https://www.postgresql.org/download/)**

### Clone o repositório via Git bash:

```
git clone https://github.com/MateusVasc/Achei-e.git
```

### Defina as variáveis de Ambiente

```
DATABASE_PASSWORD
DATABASE_URL
DATABASE_USER
SECRET_KEY
```

Esta secção é dedicada a instrução de como utilizar a API do Achei-e pelo lado do cliente.
Os endpoints serão descritos logo abaixo como também o formato de suas entradas.
## URL da API

```
https://achei-e-ef3b03158fb0.herokuapp.com
```
## Convenções:

>@RequestBody: Indica que naquele exemplo, está sendo usado um JSON no BODY da requisição
>
>
> Exemplo: 
>  ```
>  {
>    "email": "mateus-teste@upe.com.br",
>    "senha": "senh@s3gur4"
>  }
>  ```
 
> @PathVariable: Indica que naquele exemplo, está sendo usada uma variável no PATH da requisição
>
>
> Exemplo:
>  ```
>  user/{id}
>  ```

> @RequestParams: Indica que naquele exemplo, está sendo usada uma variável nos parametros de QUERY 
>
>
> Exemplo:
> ```
> /user?name="Mateus"
> ```
> OBS: No Postman/Insomnia para fazer uma requisição com query params você utilizará o Multipart Form e o 
> preencherá com os
nomes das variáveis e seus valores


## Endpoints

### `POST` Cadastro

> ```
> /achei-e/cadastro
> ```
>
> Exemplo de utilização
>
> _@RequestBody_
>
> ```
> {
>    "nome": "Mateus",
>    "sobrenome": "Araujo",
>    "email": "mateus-teste@upe.com.br",
>    "senha": "senh@s3gur4",
>    "curso": "ENGENHARIA_DE_SOFTWARE",
>    "periodo": "QUINTO",
>    "telefone": "8891239876",
>    "foto": ""
> }
> ```

### `POST` Login

> ```
> /achei-e/login
> ```
>
> Exemplo de utilização
>
> _@RequestBody_
>
> ```
> {
>    "email": "mateusParaExcluir22222@gmail.com",
>    "senha": "m12345"
> }
> ```

### `PUT` Editar usuário

> ```
> /achei-e/user
> ```
>
> Exemplo de utilização
>
> _@RequestParam_
>
> ```
> postId: e3a3c332-18b3-4747-abe3-45a28a51a631
> ```
> _@RequestBody_
>
> ```
> {
>    "type": "PERDIDO", 
>    "item": {
>        "state": "ENCONTRADO", 
>        "category": "ELETRONICO", 
>        "descricao": "apple watch rosa perdido no lab 2",
>        "titulo": "apple watch rosa",
>        "data": "2024-05-31",
>        "foto": ""
>    }
> }
> ```

### `GET` Buscar usuário por id

> ```
> /achei-e/user/{id}
> ```
>
> Exemplo de utilização
>
> _@PathVariable_
>
> ```
> /achei-e/user/e3a3c332-18b3-4747-abe3-45a28a51a631
> ```

### `POST` Criar novo post

> ```
> /achei-e/novo-post/{usuarioId}
> ```
>
> Exemplo de utilização
>
> _@PathVariable_
>
> ```
> /achei-e/novo-post/e3a3c332-18b3-4747-abe3-45a28a51a631
> ```
> _@RequestBody_
>
> ```
> {
>    "type": "PERDIDO",
>    "item": {
>        "state": "PERDIDO", 
>        "category": "ELETRONICO", 
>        "descricao": "colchão solteiro do ben 10",
>        "titulo": "colchão",
>        "data": "2024-05-31",
>        "foto": ""
>    }
> }
> ```

### `GET` Listar todos os posts

> ```
> /achei-e/posts
> ```
>
> Exemplo de utilização
>
> ```
> /achei-e/posts
> ```

### `GET` Buscar post por id

> ```
> /achei-e/post/{id}
> ```
>
> Exemplo de utilização
>
> _@PathVariable_
>
> ```
> /achei-e/post/e3a3c332-18b3-4747-abe3-45a28a51a631
> ```

### `PUT` Editar post

> ```
> /achei-e/post
> ```
>
> Exemplo de utilização
>
> _@RequestParam_
>
> ```
> postId: e3a3c332-18b3-4747-abe3-45a28a51a631
> ```
> _@RequestBody_
>
> ```
> {
>    "type": "PERDIDO",
>    "item": {
>        "state": "PERDIDO", 
>        "category": "ELETRONICO", 
>        "descricao": "colchão solteiro do ben 10",
>        "titulo": "colchão",
>        "data": "2024-05-31",
>        "foto": ""
>    }
> }
> ```

### `DEL` Excluir post

> ```
> /achei-e/excluir-post/{idPost}
> ```
>
> Exemplo de utilização
>
> _@PathVariable_
>
> ```
> /achei-e/excluir-post/e3a3c332-18b3-4747-abe3-45a28a51a631
> ```
> _@RequestParam_
>
> ```
> idUsuario: j3b3c332-18b3-4747-abe3-45a28a51a631
> ```

### `POST` Fazer comentário

> ```
> /achei-e/post/{postId}
> ```
>
> Exemplo de utilização
>
> _@PathVariable_
>
> ```
> /achei-e/post/e3a3c332-18b3-4747-abe3-45a28a51a631
> ```
> _@RequestParam_
>
> ```
> usuarioId: e3a3c332-18b3-4747-abe3-45a28a51a631
> ```
> _@RequestBody_
>
> ```
> {
>    "assunto": "perdi meu guarda-chuva na sala tal",
> }
> ```

### `PUT` Encerrar procura

> ```
> /achei-e/post/encerrar-procura
> ```
>
> Exemplo de utilização
>
> _@RequestBody_
>
> ```
> {
>    "idUsuario": "k1b3c332-18b3-4747-abe3-45a28a51a631",
>    "idPost": "p2b3c332-18b3-4747-abe3-45a28a51a631"
> }
> ```

## Autores

| [<img src="https://avatars0.githubusercontent.com/MateusVasc" width="100px;"/><br /><sub><b>Mateus Vasconcelos</b></sub>](https://www.linkedin.com/in/mateus-vasconcelos-araujo/)<br /> | [<img src="https://avatars.githubusercontent.com/u/122832634?v=4" width="100px;"/><br /><sub><b>Pedro Correia</b></sub>](https://github.com/Pedro-Bezerra)<br /> |
| :----------------------------------------------------------------------------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------------------------------------------------------------------------: |

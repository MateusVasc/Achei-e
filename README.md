<div align="center">

# SIGU

  <p align="center">
    Sistema Integrado de Gestão Universitária
    <br />
    <br />
    <a href="https://github.com/MateusVasc/Achei-e/issues">Reportar Bug</a>
    ·
    <a href="https://github.com/MateusVasc/Achei-e/issues">Solicitar Funcionalidade</a>
  </p>
</div>

---

## Sobre o Projeto

O SIGU é um projeto com foco em **arquitetura de microsserviços**, utilizando práticas modernas de desenvolvimento distribuído e automação.

Este sistema visa criar um ecossistema de serviços voltados à gestão universitária, com foco inicial em autenticação, na criação de um sistema de achados e perdidos e extensibilidade futura para outras áreas da instituição.

---

## 🧱 Arquitetura

### Sobre Microsserviços

#### O que motivou a escolha por microsserviços?

O SIGU (Sistema Integrado de Gestão Universitária) é uma plataforma projetada para centralizar e resolver múltiplas necessidades da Universidade de Pernambuco. Algumas dessas necessidades já são conhecidas — como a autenticação de usuários ou um sistema de achados e perdidos — mas muitas outras ainda surgirão com o tempo.

Por esse motivo, optar por uma arquitetura baseada em microsserviços oferece flexibilidade e escalabilidade. Cada funcionalidade é implementada como um serviço independente, o que permite que novos módulos sejam adicionados sem impactar diretamente os serviços existentes. Essa abordagem facilita o crescimento incremental da plataforma, promovendo des acoplamento, resiliência, e implantação independente de cada componente do sistema.

#### Como os domínios estão separados?

A separação de domínios no SIGU segue o princípio de responsabilidade única. Cada serviço é responsável por um conjunto específico de funcionalidades relacionadas a um domínio da universidade. Por exemplo:

- **Auth Service:** Responsável por autenticação e autorização de usuários, gerenciamento de tokens, registro e login.
- **Achei Service:** Cuida das operações relacionadas a objetos perdidos e encontrados dentro da instituição.
- **Outros módulos (a serem adicionados):** Como o sistema foi desenhado com extensibilidade em mente, novos serviços poderão ser introduzidos facilmente, como um módulo de avisos, agendamentos, central de atendimento, etc.

Os serviços se comunicam entre si através de HTTP e, quando necessário, por meio de mensageria assíncrona usando um broker como RabbitMQ, o que promove desacoplamento ainda maior.

#### Quais vantagens essa arquitetura traz pro projeto?

#### Desafios enfrentados:

### Sobre Service Discovery

### Sobre Gateways

### Serviços Atuais

| Serviço         | Função                                                 |
|-----------------|--------------------------------------------------------|
| `eureka`        | Service discovery com Spring Cloud Eureka              |
| `gateway`       | API Gateway para roteamento centralizado               |
| `auth-service`  | Autenticação, registro, emissão de JWT                 |
| `acheie-service`| Gestão de achados e perdidos na instituição            |

### Comunicação

- ✅ Registro de serviços via Eureka
- ✅ Roteamento via Spring Cloud Gateway
- 🔜 REST entre microserviços
- 🔜 Mensageria assíncrona (RabbitMQ/Kafka) para eventos

---

## Tecnologias Utilizadas

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring-Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F.svg?style=for-the-badge&logo=Spring-Boot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-6DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED.svg?style=for-the-badge&logo=docker&logoColor=white)
![Postgres](https://img.shields.io/badge/PostgreSQL-4169E1.svg?style=for-the-badge&logo=PostgreSQL&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36.svg?style=for-the-badge&logo=apachemaven&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-2088FF.svg?style=for-the-badge&logo=githubactions&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D.svg?style=for-the-badge&logo=swagger&logoColor=black)

- Java + Spring Boot
- Spring Cloud (Eureka, Gateway)
- Docker
- PostgreSQL
- Maven
- GitHub Actions (CI/CD - em progresso)
- Testes com JUnit + Mockito
- OpenAPI/Swagger (futuro)

---

## Configuração do ambiente de desenvolvimento

### 1. Clone o repositório

```
git clone https://github.com/MateusVasc/Achei-e.git
```

### 2. Defina as variáveis de Ambiente

```
cp .env.example .env
```

### 3. Suba os serviços

```
docker-compose up --build
```

---

## Utilizando a API

Esta secção é dedicada ao front-end da aplicação, instruindo como utilizar a API do SIGU pelo lado do cliente.
Os endpoints serão descritos logo abaixo como também o formato de suas entradas.

### Base URL da API

```
http://localhost:8080
```
### Convenções:

>@RequestBody: Indica que naquele exemplo, está sendo usado um JSON no BODY da requisição
>
>
> Exemplo: 
>  ```
>  {
>    "email": "mateus-teste@upe.com.br",
>    "senha": "Senh@s3gur4"
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


### Endpoints

### `POST` Cadastro

> ```
> /auth-service/api/auth/register
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
> /auth-service/api/auth/login
> ```
>
> Exemplo de utilização
>
> _@RequestBody_
>
> ```
> {
>    "email": "mateusParaTeste22222@gmail.com",
>    "senha": "m12345"
> }
> ```

---

## Autores

| [<img src="https://avatars0.githubusercontent.com/MateusVasc" width="100px;"/><br /><sub><b>Mateus Vasconcelos</b></sub>](https://www.linkedin.com/in/mateus-vasconcelos-araujo/)<br /> | [<img src="https://avatars.githubusercontent.com/u/122832634?v=4" width="100px;"/><br /><sub><b>Pedro Correia</b></sub>](https://github.com/Pedro-Bezerra)<br /> |
| :----------------------------------------------------------------------------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------------------------------------------------------------------------: |

## API REST para o Administração de Stocks da Bolsa de Valores
> A API REST consiste de uma ferramenta de consulta utilizando técnica de paginação para performance e realizando cálculos de geolocalização.

[![Spring Badge](https://img.shields.io/badge/-Spring-brightgreen?style=flat-square&logo=Spring&logoColor=white&link=https://spring.io/)](https://spring.io/)
[![Maven Badge](https://img.shields.io/badge/-Maven-000?style=flat-square&logo=Apache-Maven&logoColor=white&link=https://maven.apache.org/)](https://maven.apache.org/)
[![MySQL Badge](https://img.shields.io/badge/-MySQL-informational?style=flat-square&logo=MySQL&logoColor=white&link=https://www.mysql.com/)](https://www.mysql.com/)
[![Heroku Badge](https://img.shields.io/badge/-Heroku-blueviolet?style=flat-square&logo=Heroku&logoColor=white&link=https://id.heroku.com/)](https://id.heroku.com/)


<img align="right" width="400" height="300" src="https://github.com/willdkdevj/assets/blob/main/Spring/spring-framework.png">

## Descrição da Aplicação
A aplicação consiste em uma API (*Application Programming Interface*) REST (*Representational State Transfer*), sendo aplicado o modelo cliente/servidor na qual tem a função de enviar e receber dados através do protocolo HTTP, sendo o seu principal objetivo permitir a interoperabilidade entre aplicações distintas. Mas nesta aplicação, o intuito é criar uma API que se comunica com uma Interface de Usuário (UI) na qual apresenta *tickers*, que comunicam ao investidor informações essenciais sobre sua aplicação na **Bolsa de Valores**. Desta forma, a API será a função de interagir com a interface a fim de administrar os tickers, fornecendo uma camada de CRUD para sua manipulação.

No decorrer deste documento é apresentado com mais detalhes de sua implementação, descrevendo como foi desenvolvida a estrutura da API, suas dependências e como foi colocado em prática a implementação dos cálculos e listagem por paginação. Além disso, como foi implementado o Spring Boot, para agilizar a construção do código e sua configuração, conforme os *starters* e as suas dependências. Bem como, o Spring Data JPA, que nos dá diversas funcionalidades permitindo uma melhor dinâmica nas operações com bancos de dados e sua manutenção. Até o seu deploy na plataforma Heroku para disponibilizá-la pela nuvem ao cliente.

## Principais Frameworks
Os frameworks são pacotes de códigos prontos que facilita o desenvolvimento de aplicações, desta forma, utilizamos estes para obter funcionalidades para agilizar a construção da aplicação. Abaixo segue os frameworks utilizados para o desenvolvimento este projeto:

**Pré-Requisito**: Java 11 (11.0.10 2021-01-19 LTS) / Maven 4.0.0 

| Framework       | Versão | Função                                                                                            |
|-----------------|:------:|---------------------------------------------------------------------------------------------------|
| Spring Boot     | 2.4.4  | Permite agilizar o processo de configuração e publicação de aplicações do ecossistema Spring      |
| Spring Actuator | 2.4.4  | Fornece endpoints que permite verificar o estado da aplicação através de métricas                 |
| Spring Data JPA | 2.4.4  | Facilita na interação com database permitindo uma fluídez na persistência dos dados de modo geral |
| Hibernate       | 6.1.7  | Permite automatizar as tarefas com o banco de dados facilitando o código da aplicação             |
| Lombok          | 1.18.18| Permite reduzir a verbosidade do código através de anotações                                      |
| MapStruct       | 1.4.1  | Permite o mapeamento entre bean Java com base de uma abordagem de conversão sobre configuração    |
| JUnit 	      | 5.7.1  | Permite a realização de testes unitários de componentes da aplicação Java                         |
| Mockito         | 3.6.28 | Permite criar objetos dublês a fim de realizar testes de unidade em aplicações Java               |
| OpenAPI         | 2.9.2  | Possibilita a definição e a criação de modo estruturado a documentação de API REST                | 


## A Hospedagem na Plataforma Heroku
Para hospedar nosso código na plataforma **Heroḱu** é necessário criar uma conta e atrelá-la a conta no **GitHub**, desta forma, ao logar no *Dashboard* do Heroku é criado um novo aplicativo apontando a conta do GitHub informando o nome do repositório em que está o projeto. Além disso, é habilitado a opção de *deploy* automático, para que todas as vezes que for realizado um *PUSH* para o repositório seja realizado o deploy da aplicação.

Como a aplicação está com a versão 11 do Java é necessário passar um parâmetro de configuração ao Heroku, pois por padrão, o Heroku suporta aplicações com a versão 8. Desta forma, no diretório raiz do projeto é criado o arquivo de configuração ``system.properties`` com o seguinte snippet
```sh
java.runtime.version=11
```

Este processo de criação foi realizado antes de "subir" a aplicação para a plataforma, desta maneira, ela reconhece a aplicação com a versão informada.

![Deploy Heroku](https://github.com/willdkdevj/assets/blob/main/Heroku/deploy_heroku_person.png)

Para acessar a aplicação diponibilizada em *cloud*, acesse o seguinte link <https://apipeople-dio.herokuapp.com/api/v1/people>. Desta forma, é possível realizar as interações com a ferramenta das requisições HTTP.

## Como Está Documentado o Projeto
O framework ``Swagger UI`` auxilia na criação da documentação do projeto, por possuir uma variedade de ferramentas que permite modelar a descrição, consumo e visualização de serviços da API REST, e o spring possui uma implementação própria denominada **OpenAPI**. No projeto foi incluída sua dependência (``springdoc-openapi-ui``) para habilitá-lo para uso na aplicação, desta forma, no *snippet* abaixo é apresentado o Bean principal para sua configuração, presente na classe SwaggerConfig.

```java
@Bean
public OpenAPI customOpenAPI(){
	return new OpenAPI().info(new Info()
			.title("REST API - Tickers Panel")
			.description("Bootcamp Santander project for the creation of a Panel of Tickers to monitor the actions of companies on the Stock Exchange")
			.contact(infoContact())
			.version("1.0")
			.termsOfService("http://swagger.io/terms")
			.license(new License().name("Apache 2.0").url("http://springdoc.org")));
}
```

A especificação da API consiste na determinação de parâmetros de identificação e os modelos de dados que serão aplicados pela API, além de suas funcionalidades. Entretanto, o Swagger por padrão lista todos os endpoints retornando os códigos 200, 201, 204,401,403 e 404, mas é possível especificar quais são os códigos do protocolo HTTP que sua aplicação utiliza ao utilizar a anotação @ApiResponses.

![Framework Project - Test](https://github.com/willdkdevj/assets/blob/main/Spring/openapi_tickers_panel.png)

Desta forma, o framework Swagger possibilita documentar a API REST passando informações sobre a API e seus desenvolvedores, além disso, caracteristicas de sua estrutura de um modo ágil de eficiente. Sua exposição é feita através do link <http://localhost:8080/tickers-panel/swagger-ui.html>

## Como Executar o Projeto

```bash
# Para clonar o repositório do projeto, através do terminal digite o comando abaixo
git clone https://github.com/willdkdevj/RESTAPI_TICKERPANEL_SPRING.git

# Para acessar o diretório do projeto digite o comando a seguir
cd /RESTAPI_TICKERPANEL_SPRING

# Executar projeto via terminal, digite o seguinte comando
./mvnw spring-boot:run

# Para Executar a suíte de testes desenvolvidas, basta executar o seguinte comando
./mvnw clean test
```

Para testar a API, como a aplicação consome e produz JSON, é necessário utilizar uma ferramenta que permite realizar requisições HTTP neste formato, como o Postman, Insomnia, entre outras. Na diretório ``JSON-TEST-HTTP/``  há um arquivo que pode ser importado a qualquer uma destas ferramentas, onde já estarão formatados os tipos de requisições suportadas pela API para a realização dos testes.

## Agradecimentos
Obrigado por ter acompanhado aos meus esforços para desenvolver este Projeto utilizando a estrutura do Spring para criação de uma API REST 100% funcional, utilizando os recursos do Spring data JPA para facilitar as consultas, o padrão DTO para inclusão e atualização dos dados, além de, listar grandes quantidades de dados paginas, com ordenação e busca, utilizando os conceitos do TDD para implementar testes de integração para validar nossos endpoints com o MockMVC e gerar a documentação de forma automática através do Swagger(OpenAPI)! :octocat:

Como diria um velho mestre:
> *"Cedo ou tarde, você vai aprender, assim como eu aprendi, que existe uma diferença entre CONHECER o caminho e TRILHAR o caminho."*
>
> *Morpheus - The Matrix*
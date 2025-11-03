# API MongoDB – Igesta 2º Ano

> Projeto backend em Java + Spring Boot + MongoDB, desenvolvido pela A‑UTech para gestão de dados no contexto “Igesta 2º ano”.

## 📝 Descrição

Esta API foi criada para servir como backend de um sistema de gestão utilizando:

* Java (Spring Boot)
* Banco de dados NoSQL: MongoDB
* Infraestrutura containerizada via Docker / docker-compose
* Estrutura de projeto preparada para CI/CD (wrapper Maven, Dockerfile, workflows)

O objetivo principal é oferecer endpoints REST para a criação, leitura, atualização e deleção (CRUD) de entidades do domínio, de forma escalável e flexível.

## 🚀 Funcionalidades principais

* Conexão com banco MongoDB (local ou em container)
* Endpoints REST para entidades (ex: tarefas, usuários, atendimentos, etc)
* Suporte para operações CRUD completas
* Configuração via `application.properties` ou variáveis de ambiente
* Deploy via contêiner Docker, ou através de `docker-compose.yml` para desenvolvimento/homologação

## 🧰 Tecnologias utilizadas

* Java 11+ (ou versão definida no `pom.xml`)
* Spring Boot (Web, Data MongoDB)
* MongoDB
* Maven (wrapper incluído: `mvnw`, `mvnw.cmd`)
* Docker / Docker Compose
* GitHub Actions (workflow presente)

## 📦 Estrutura do Projeto

```
.github/
└── workflows/       # configuração CI/CD
mvnw, mvnw.cmd       # scripts wrapper Maven
pom.xml              # dependências e configurações Maven
Dockerfile           # build da imagem Docker da API
docker-compose.yml   # orquestração: MongoDB + API
src/
└── main/java/...    # código-fonte em Java
src/
└── main/resources/
    └── application.properties  # configurações da aplicação
```

## 🚀 Como executar localmente

1. Clone o repositório:

```
git clone https://github.com/A-UTech/api-mongodb-igesta-2ano.git
cd api-mongodb-igesta-2ano
```

2. Certifique-se de ter instalado: Docker, Docker Compose, Java e Maven (ou use os wrappers).
3. Iniciar via Docker Compose:

```
docker-compose up --build
```

Isso levantará o MongoDB e a API automaticamente.
4. Ou rodar apenas a API localmente:

```
./mvnw spring-boot:run
```

ou

```
mvn spring-boot:run
```

5. Acesse a API (por padrão) em: `http://localhost:8080/`

## 🔧 Configuração

Configurações podem ser feitas em `src/main/resources/application.properties` ou via variáveis de ambiente:

Exemplo `application.properties`:

```
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=igesta_db
server.port=8080
```

Ou via variáveis:

```
MONGODB_HOST=localhost
MONGODB_PORT=27017
MONGODB_DATABASE=igesta_db
SERVER_PORT=8080
```

## 🧠 Boas práticas

* Logs adequados (SLF4J)
* Tratamento global de exceções (`@ControllerAdvice`)
* Validação de entradas (`@Valid`, `@NotNull`)
* Uso de DTOs para respostas
* Documentação da API (Swagger / SpringDoc OpenAPI)

## 📁 Deployment / Produção

* Configurações de segurança: autenticação/autorização, CORS, variáveis secretas
* Backup do MongoDB
* Uso de servidores ou serviços de contêiner (AWS ECS, Azure, Heroku)
* Monitoramento (Prometheus, Grafana)

## 🤝 Contribuições

1. Fork do repositório
2. Crie sua branch (`git checkout -b feature/NovaFuncionalidade`)
3. Commit (`git commit -m 'Adiciona nova funcionalidade X'`)
4. Push (`git push origin feature/NovaFuncionalidade`)
5. Abra um Pull Request

## 📜 Licença

Licenciado sob MIT — veja o arquivo LICENSE.

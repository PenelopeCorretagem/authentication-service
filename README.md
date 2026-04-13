# Auth Service (Penelope)

Microservico de autenticacao da plataforma Penelope.

O servico centraliza login com JWT e fluxo de recuperacao de senha por codigo enviado por e-mail.

## Visao geral

- Base path da API: `/api/v1/auth`
- Porta padrao: `8082` (configuravel por variavel de ambiente)
- Perfil padrao: `dev`
- Banco em `dev`: H2 em memoria
- Banco em `prod`: MySQL + Flyway

## Stack tecnica

- Java 21
- Spring Boot 3.5.5
- Spring Web
- Spring Data JPA
- Spring Validation
- Spring Mail
- Spring Security Crypto (BCrypt)
- java-jwt (Auth0)
- Flyway
- H2 (dev)
- MySQL (prod)

## Arquitetura

O projeto segue uma organizacao em camadas:

- `application`: DTOs e casos de uso
- `core`: entidades, value objects, gateways e excecoes de dominio
- `infrastructure`: adapters, configuracoes, persistencia JPA e camada web

## Requisitos

- JDK 21
- Maven 3.9+
- Conta SMTP valida (para fluxo de reset)
- MySQL (apenas para perfil `prod`)

## Configuracao de ambiente

O projeto suporta `.env` na raiz e em `src/main/resources/.env`.

Use o arquivo de exemplo:

```bash
cp src/main/resources/.env.example .env
```

Variaveis principais:

| Variavel | Obrigatoria | Default | Descricao |
| --- | --- | --- | --- |
| `SPRING_PROFILES_ACTIVE` | Nao | `dev` | Perfil ativo (`dev` ou `prod`) |
| `AUTH_SERVICE_PORT` | Nao | `8082` | Porta HTTP da aplicacao |
| `JWT_API_KEY` | Sim | - | Segredo usado para assinar/validar JWT |
| `APP_FRONTEND_URL` | Nao | `http://localhost:3000` | Base URL usada no e-mail de reset |
| `EMAIL` | Sim (reset por e-mail) | - | Conta SMTP remetente |
| `EMAIL_PASSWORD` | Sim (reset por e-mail) | - | Senha/app-password SMTP |
| `DB_HOST` | Sim em `prod` | - | Host do MySQL |
| `DB_PORT` | Sim em `prod` | - | Porta do MySQL |
| `DB_NAME` | Sim em `prod` | - | Nome do schema |
| `DB_USER` | Sim em `prod` | - | Usuario MySQL |
| `DB_PASSWORD` | Sim em `prod` | - | Senha MySQL |

## Executando localmente

### 1) Rodar em desenvolvimento (H2)

```bash
mvn spring-boot:run
```

Comportamento no perfil `dev`:

- Banco H2 em memoria (`jdbc:h2:mem:authservice`)
- `ddl-auto=create-drop`
- Console H2 habilitado em `/api/h2-console`

### 2) Rodar em producao local (MySQL)

```bash
SPRING_PROFILES_ACTIVE=prod mvn spring-boot:run
```

No Windows PowerShell:

```powershell
$env:SPRING_PROFILES_ACTIVE = "prod"
mvn spring-boot:run
```

Comportamento no perfil `prod`:

- DataSource MySQL
- `ddl-auto=validate`
- Flyway habilitado com location `classpath:db/migration/auth`

Observacao importante:

- A pasta `src/main/resources/db/migration/auth` esta vazia no estado atual do repositorio.

## Endpoints

Base URL local:

```text
http://localhost:8082/api/v1/auth
```

### POST `/login`

Autentica usuario por e-mail e senha.

Request:

```json
{
	"email": "usuario@exemplo.com",
	"password": "senha123"
}
```

Response `200`:

```json
{
	"token": "<jwt>",
	"id": 1,
	"accessLevel": "CLIENTE"
}
```

### POST `/forgot-password`

Gera codigo de reset e envia por e-mail se o usuario existir.

Request:

```json
{
	"email": "usuario@exemplo.com"
}
```

Response `200`:

```json
{
	"message": "Se o e-mail estiver cadastrado, um codigo de verificacao sera enviado."
}
```

### POST `/validate-reset-token`

Valida o codigo/token de reset.

Request:

```json
{
	"token": "123456"
}
```

Response `200`:

```json
{
	"message": "Token valido."
}
```

### POST `/reset-password`

Aplica nova senha para o usuario dono do token.

Request:

```json
{
	"token": "123456",
	"newPassword": "NovaSenhaForte@123"
}
```

Response `200`:

```json
{
	"message": "Senha redefinida com sucesso."
}
```

## Regras de seguranca e autenticacao

- JWT assinado com `HMAC256`
- Claim `accessLevel` incluida no token
- Issuer: `Penelope-API`
- Expiracao do JWT: 2 horas
- Token de reset: codigo numerico de 6 digitos
- Expiracao do token de reset: 1 hora
- Senhas persistidas com BCrypt

## Niveis de acesso

Enum atual:

- `ADMINISTRADOR`
- `CLIENTE`

## Padrao de erro da API

As excecoes sao tratadas globalmente e retornam payload padrao:

```json
{
	"status": 400,
	"message": "Token expirado. Solicite um novo codigo.",
	"timestamp": "2026-04-12T10:00:00"
}
```

Mapeamento principal:

- `401 Unauthorized`: credenciais invalidas
- `400 Bad Request`: validacoes de dominio (ex.: token invalido/expirado)
- `502 Bad Gateway`: falhas de integracao (ex.: geracao de JWT)
- `500 Internal Server Error`: erro nao tratado

## Colecao Postman

Existe uma colecao pronta no repositorio:

- `Auth.postman_collection.json`

## Testes

Executar testes:

```bash
mvn test
```

## Estrutura resumida

```text
src/main/java/penelope/corretagem/authservice
|- application
|- core
|- infrastructure
```
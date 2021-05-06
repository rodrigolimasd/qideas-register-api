# Microservice Qideas Register Api [![MIT license](http://img.shields.io/badge/license-MIT-brightgreen.svg)](https://github.com/rodrigolimasd/qideas-register-api/blob/master/LICENSE)

This is the registration microservice for the Qideas application.

It is a RESTful API built with Java 11 + SpringBoot + Spring Security + Open Feing + MongoDB that receives all data related to users and records / provides the customer with all this data through a REST API.

## Getting Started

### Prerequisites

To run this project in development mode, you will need to have a basic environment with Java JDK 11+ and Maven 3.5.4+ installed. To use the database, you will need to have MongoDB installed and running on your machine on the default port (27017).

This project will require external services, so in addition to this the following projects must be configured: 

**Microservice Qideas Auth Api** https://github.com/rodrigolimasd/qideas-auth-api

### Installing

**Cloning the Repository**
````
$ git clone https://github.com/rodrigolimasd/qideas-register-api.git
$ cd qideas-register-api
````
### Running the Development

**Running with Maven**
```
$ mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

To run the tests use the following command

**Running the tests**

```
$ mvn clean test
```

### Consuming the API

**Creating An Account**
```
$ curl --location --request POST 'http://localhost:8080/v1/accounts' \
  --header 'Content-Type: application/json' \
  --data-raw '{
      "name":"test User",
      "email":"usertest@email.com",
      "username": "usertest",
      "password": "A@password123"
  }'
```

Response Status Code: **201**
```
$ {"id":"60935bb91439907d268b2df1","criacao":null,"edicao":null,"name":"test User","birthday":null,"email":"usertest@email.com"}% 
```
**Updating Account**

Note: Before searching for an account, you need to authenticate with the **Qideas Auth Api authentication api**.
All account changes will take effect for the account that is authenticated according to the obtained token

```
$ curl --location --request PUT 'http://localhost:8080/v1/accounts' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6Ik.....' \
--header 'Content-Type: application/json' \
--data-raw '{
      "name":"test User update",
      "email":"usertest@email.com",
      "username": "usertest"
  }'
```
Response Status Code: **204**

**Get Info Account**

```
$ curl --location --request GET 'http://localhost:8080/v1/accounts/info' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic2V....'
```
Response Status Code: **200**
```
$ {"id":"60935bb91439907d268b2df1","criacao":null,"edicao":null,"name":"test User update","birthday":null,"email":"usertest@email.com"}% 
```

**Deleting Account**

```
$ curl --location --request DELETE 'http://localhost:8080/v1/accounts' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic2Vyd...'
```
Response Status Code: **204**

## Contributing

E-mail: rodrigolimasd@gmail.com

Connect with me at [LinkedIn](https://www.linkedin.com/in/rodrigolimasd/)

Thank you!



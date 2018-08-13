# SurveyMonkey

This project is a demonstration of usage os MicroServices with Spring Boot implementation and providing API endpoints for conducting surveys by multiple choice questions.

## Used Technologies

Spring Boot 2 is used as a runner. The only application which uses Spring Boot 1.5.15 is the question-service application. This is done to support swagger with spring-data-rest. As of now, swagger does not support Spring Boot 2 with spring-data-rest. 

In addition to this, below technologies are used:
- JPA and H2 in-memory database for storing and retrieving data
- Spring data-rest to make crud APIS for questions and answers
- Eureka for registering MicroServices
- Zuul Proxy for redirecting the requests to a right microservice
- JUnit, Mockito and Hamcrest for testing

## Getting Started

For running the project you must have the following software tools installed in your machine:

```$xslt
- JDK8
- Maven 3.5+
```

Follow below steps to run the application:

1. navigate to project root folder in terminal(Linux) or CMD(windows)
2. run **mvn clean install** which will run unit and integration test cases and generate runnable jar files.
3. run application in the following order:
    3. Navigate to SurveyMonkey\eureka-service\target and run **java -jar eureka-service-0.0.1.jar**
    3. Navigate to SurveyMonkey\config-service\target and run **java -jar config-service-0.0.1.jar**
    3. Navigate to SurveyMonkey\question-service\target and run **java -jar question-service-0.0.1.jar**
    3. Navigate to SurveyMonkey\survey-service\target and run **java -jar survey-service-0.0.1.jar**
    3. Navigate to SurveyMonkey\api-gateway\target and run **java -jar api-gateway-0.0.1.jar**

###### Now the application is fully running. You can open swagger ui and test it:
Swagger ui for Question Answers MicroService is here: **http://{host}:8080/question-service/swagger-ui.html**

Swagger ui for Survey MicroService is here: **http://{host}:8080/survey-service/swagger-ui.html**

Some data is saved during the start of application in order to make testing easier.
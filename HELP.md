# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

## Main commands and urls

1. * Execute jar form terminal, passing spring boot profile, change the path, and change the profile by your desired *
```shell
java - jave $HOME/.m2/repository/com/paymentchain/customer/0.0.1-SNAPSHOT/customer-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```
2. [Swagger interface](http://localhost:8081/swagger-ui/index.html#/)
3. [REST get Method check profile on customer] (http://localhost:8081/api/v1/customer/check)
4. [Access to h2 database] (http://localhost:8081/h2-console/)
5. [REST get method config from spring config] (http://localhost:8081/api/v1/customer-dev/development)

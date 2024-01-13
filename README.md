[Проект TopJava-2](https://javaops.ru/view/topjava2)

===============================

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c60328a3606d46e39db332a1e771c9e8)](https://app.codacy.com/gh/ProYulia/topjava2-master?utm_source=github.com&utm_medium=referral&utm_content=ProYulia/topjava2-master&utm_campaign=Badge_Grade)

### Task
Design and implement a REST API using Hibernate/Spring/SpringMVC (Spring-Boot preferred!) **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

*  2 types of users: admin and regular users
*  Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
*  Menu changes each day (admins do the updates)
*  Users can vote for a restaurant they want to have lunch at today
*  Only one vote counted per user
*  If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.

-------------------------------------------------------------
### Stack
- Stack: [JDK 17](http://jdk.java.net/17/), Spring Boot 3.x, Lombok, H2, Caffeine Cache, SpringDoc OpenApi 2.x, Mapstruct 
- Run: `mvn spring-boot:run` in root directory.
-----------------------------------------------------
 
### Test Data
```
User:  user@yandex.ru / password
Admin: admin@gmail.com / admin
Guest: guest@gmail.com / guest
```
### Swagger UI

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
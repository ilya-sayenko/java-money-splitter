# Money splitter
[Kitty Split](https://www.kittysplit.com/ru/) analogue. Service for splitting bills.

Stack: Java, Spring Boot, PostgreSQL.

## How to use with Docker
You can run backend server with only a single command:
```
docker-compose up -d
```

Stopping all the running containers is also simple with a single command:
```
docker-compose down
```

After starting you can see Swagger using url:
```
http://localhost:8080/swagger-ui/index.html#/
```
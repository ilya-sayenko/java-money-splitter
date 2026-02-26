# Multi-stage build для оптимизации размера образа
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Копируем pom файлы для кэширования зависимостей
COPY pom.xml .
COPY money-splitter-core/pom.xml ./money-splitter-core/
COPY money-splitter-rest/pom.xml ./money-splitter-rest/
COPY money-splitter-console/pom.xml ./money-splitter-console/

# Загружаем зависимости (этот слой будет кэшироваться)
RUN mvn dependency:go-offline -B

# Копируем исходный код
COPY money-splitter-core ./money-splitter-core
COPY money-splitter-rest ./money-splitter-rest
COPY money-splitter-console ./money-splitter-console

# Собираем приложение
RUN mvn clean package -DskipTests

# Финальный образ
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Копируем собранный JAR файл
COPY --from=build /app/money-splitter-rest/target/*.jar app.jar

# Открываем порт
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]

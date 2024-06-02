# Первый этап: Сборка проекта
FROM maven:3.8.1-openjdk-11 AS build

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем файл pom.xml и скачиваем зависимости
COPY pom.xml .
RUN mvn dependency:go-offline

# Копируем весь проект и собираем его
COPY . .
RUN mvn package -DskipTests

# Второй этап: Создание минимального образа для запуска
FROM openjdk:11-jre-slim

# Создаем рабочую директорию
WORKDIR /app

# Копируем сгенерированный jar файл из предыдущего этапа
COPY --from=build /app/target/*.jar app.jar

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]

# Открываем порт 8088 для приложения
EXPOSE 8088
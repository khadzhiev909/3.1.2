# Spring Boot Security Demo

![Spring Boot Logo](https://spring.io/images/spring-logo.svg)

## Описание проекта

**Spring Boot Security Demo** - это демонстрационное приложение на основе Spring Boot, созданное для демонстрации аутентификации и авторизации пользователей, а также для управления пользователями через административную панель. 

## Функциональность

- **Аутентификация и авторизация:** Пользователи могут аутентифицироваться в системе с помощью логина и пароля. Авторизация реализована с использованием Spring Security.
- **Административная панель:** Администраторы имеют доступ к панели управления пользователями, где они могут просматривать, добавлять, редактировать и удалять пользователей из системы.

## Технологии

### Backend

- Java 11
- Spring Boot 2.6.2
- Spring Security
- Spring Data JPA
- Hibernate ORM
- Lombok

### Frontend

- Thymeleaf - шаблонизатор
- JavaScript для выполнения запросов с помощью Fetch API

### База данных

- MySQL 8.0.25

### Дополнительно

- Docker - для контейнеризации приложения

## Запуск проекта

### Локально

1. Убедитесь, что у вас установлены Java 11 и MySQL 8.0.25.
2. Импортируйте проект в вашу IDE.
3. Запустите MySQL сервер и создайте базу данных с именем "users".
4. Убедитесь, что в файле `application.properties` указаны корректные данные для подключения к базе данных.
5. Запустите приложение из вашей IDE.

### Docker

1. Убедитесь, что у вас установлен Docker на вашем компьютере.
2. Соберите Docker-образ приложения с помощью команды `docker build -t spring-boot-security-demo .` в корневом каталоге проекта.
3. Запустите контейнер из собранного образа с помощью команды `docker run -p 8088:8088 spring-boot-security-demo`.

## Дополнительные настройки

- Для изменения порта приложения или других конфигурационных параметров, отредактируйте файл `application.properties`.
- Для настройки доступа к административной панели, включая роли пользователей и права доступа, измените соответствующие настройки в файле `SecurityConfig.java`.

## Автор

[Ваше имя]

## Docker Hub

Вы также можете загрузить Docker-образ этого приложения из [khadzhiev404/admin-panel](https://hub.docker.com/r/khadzhiev404/admin-panel).


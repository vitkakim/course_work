# Курсовой проект по модулю «Автоматизация тестирования» для профессии «Инженер по тестированию»

Курсовой проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

### Документы

* [План автоматизации](documents/Plan.md)

### Установка и запуск

1. Запустить Docker - в терминале ввести команду `docker-compose up -d`
2. Запустить файл JAR-файл - в терминале ввести команду `java -jar artifacts/aqa-shop.jar`
3. Запуск тестов `gradlew clean test`
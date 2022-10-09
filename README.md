## Дипломная работа профессии "Тестировщик"

### Документация:

1. План автоматизации тестирования веб-сервиса "Путешествие дня" [Plan.md](https://github.com/95kreal/QA-Diploma/blob/main/Docs/Plan.md)
2. Отчёт по итогам тестирования [Report.md](https://github.com/95kreal/QA-Diploma/blob/main/Docs/Report.md)
3. Отчёт по итогам автоматизации [Summary.md](https://github.com/95kreal/QA-Diploma/blob/main/Docs/Summary.md)


### Для запуска приложения:

1. С помощью Git cклонировать репозиторий командой `git clone https://github.com/95kreal/QA-Diploma.git`;
2. Запустить Docker;
3. Открыть проект в IntelliJ IDEA;
4. В файле `build.gradle` раскомментировать строку в параметрах `test`, соответсвующее необходимому подключению к СУБД (по стандарту установлено MySQL)
5. В терминале IntelliJ IDEA запустить необходимые базы данных и нужные контейнеры командой `docker-compose up`;
6. В новой вкладке терминала ввести следующую команду:  
      `java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar aqa-shop.jar` для БД MySQL;  
      `java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar aqa-shop.jar` для БД PostgreSQL;
7. Проверить доступность приложения в браузере по адресу:  
   `http://localhost:8080/`

### Для запуска автотестов:

- В новой вкладке терминала ввести следующую команду:  
      `./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"` для БД MySQL;  
      `./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"` для БД PostgreSQL;

### Для просмотра отчетов по результатам тестирования:

- Нажать на ссылку в терминале IntelliJ IDEA:  
      `file:///C:/Program%20Files/JavaProjects/QA-Diploma/build/reports/tests/test/index.html`

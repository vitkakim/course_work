### Краткое описание
В данном проекте было проведено автоматизированное тестирование веб-сервиса для приобретения путевок с онлайн-оплатой.

В ходе проверки были проведены тесты UI, тестирование записи в БД.
### Количество тест-кейсов
Было автоматизировано 43 тест-кейса:

1. 41 UI теста
2. 2 теста с запросами в БД

### % успешных/не успешных
Из 43 тест-кейсов: 24 не прошли, 19 прошли.

Итого: 44% (успешных) / 56% (неуспешных)

![](report.png)
### Общие рекомендации
1. Устранить обнаруженные баги согласно Issue
2. Для поля "Владелец" настроить допустимые символы и не допустимые символы 
3. Убрать второе всплывающее уведомление при вводе DECLINE-карты (Ошибка, Успешно)
4. Разработать документацию на БД.
5. Добавить CSS селекторы для тестирования

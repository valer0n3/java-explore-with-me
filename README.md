# java-explore-with-me

https://github.com/valer0n3/java-explore-with-me/pull/6

#### ExploreWithMe - Приложение позволяет пользователям делиться информацией об интересных событиях и находить компанию для участия в них

1) API сервиса статистики
    - https://raw.githubusercontent.com/yandex-praktikum/java-explore-with-me/main/ewm-stats-service-spec.json
2) API основного сервиса
    - https://raw.githubusercontent.com/yandex-praktikum/java-explore-with-me/main/ewm-main-service-spec.json
3) API дополнительного функционала:

- [DELETE] comments/all/event/{eventId} - удалить все комментарии определенного события
- [GET]  comments/event/{eventId} - получить все комментарии определенного события
- [POST] comments/event/{eventId}/user/{userId} - создать новый комментарий
- [DELETE] comments/{commentId}/user/{userId} - пользователь может удалить свой комментарий
- [PATCH] comments/{commentId}/user/{userId} - пользователь может изменить свой комментарий

## stats-server database diagram:

![database diagram:](statistic\stats-server\src\main\resources\explore-with-me-statistic.jpg)

## ewm-server database diagram:

![database diagram:](ewm-service\src\main\resources\explore-with-me-main.jpg)

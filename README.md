# mts-summer-school-homework

Данное приложение показывает список популярных фильмов с возможностью прочитать о них подробную информацию, посмотреть актеров и оценку.

Приложение написано в рамках летней школы от МТС, и скорее представляет учебный интерес, чем практический. Данные берутся из tmdb.

Здесь использовано множество технологий: **Room** - для реализации концепции offline-first, **Retrofit** - для получении данных о фильмах по сети, **Navigation component**, 
**coroutines**. Также, использован **Worker** для запроса данных в background'е, реализованы уведомления при помощи **firebase messanging service**, добавлены различные анимации 
(в том числе с использованием **shared element**), для оптимизации работы с recycler view используется **diffUtil**. Архитектура приложения - **паттерн MVVM**.

Пока еще в приложении не работает поиск и фильтрация фильмов по жанрам и также нет функциональности профиля, так что в ближайшем будущем планирую это реализовать

**Скриншоты из приложения**

| <img src="https://sun9-63.userapi.com/impg/XzjtrPA1JuX_1O5YC9D-wiz49bXZhaHYG3sPuQ/Nropc0TyGTU.jpg?size=1051x2160&quality=96&sign=64ce9fd7425fa6f4859b186f0692c01b&type=album" width="250">       | <img src="https://sun9-63.userapi.com/impg/m5payaHS-IINW9XmvlLDSp6SSO2c0Fv9btJUqQ/4ORpaSDIres.jpg?size=1051x2160&quality=96&sign=d34e88602aef830cac90fad12818fa57&type=album" width="250">                | <img src="https://sun9-8.userapi.com/impg/p9xmixaHPh34vDFI0Nrg7yXeHMhCTs2G8tnnvg/-UINnfM7MUE.jpg?size=1051x2160&quality=96&sign=ef12c18daddf663eb32d4d809d81d8f9&type=album" width="250"> |
| ------------- |:------------------:| -----:|
| Главный экран     | Экран деталей фильма    | Профиль |

# Неделя 2. Жизненный цикл Activity
## Теория
Изучим жизненный цикл Activity на практике.

![](activity_img/activity_lifecycle.png)

Основные методы ЖЦ:

Название | Когда срабатывает
--- | ---
onCreate |	called when activity is first created.
onStart	| called when activity is becoming visible to the user.
onResume | called when activity will start interacting with the user.
onPause	| called when activity is not visible to the user.
onStop | called when activity is no longer visible to the user.
onRestart | called after your activity is stopped, prior to start.
onDestroy	| called before the activity is destroyed.


## Приложение

Приложение состоит из 2 Activity, одна открывается из другой по кнопке. Во второй по кнопке открывается AlertDialog.

1. Приложение открылось, произведен переход на второй экран и открытие AlertDialog
![](activity_img/activity_alert.png)
2. Произведен переход обратно на первый экран
![](activity_img/activity_back.png)
3. Устройство заблокировано и разблокировано (произведена предварительная чистка логов)
![](activity_img/activity_block.png)

## Вывод

+ При переходе с первой Activity на вторую первая переходит в состояние Paused, вызывается метод onPause, затем в состояние Stopped, метод onStop
+ При возвращении обратно на первую Activity, вторая уничтожается из памяти
+ Открытие AlertDialog не вызывает никаких методов ЖЦ
+ Блокировка устройства приводит к переходу состояния открытой Activity в Stopped

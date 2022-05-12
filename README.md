# Неделя 2. Дополнительное задание: алгоритмы сортировки
В интерфейсе приложения возможен выбор из 5 видов сортировок. Проведем эксперименты по быстродействию (время без учета инициализации).

Тип сортировки | 1000 элементов | 2500 элементов | 5000 элементов | 8000 элементов | 15000 элементов | 30000 элементов
--- | --- | --- | --- | --- | --- | ---
Встроенная | < 1 мс | < 1 мс | < 1 мс | 1 мс | 2 мс | 3 мс
Расчёской | 1 мс | 2 мс | 2 мс | 3 мс | 7 мс | 16 мс
Вставками | 2 мс | 12 мс | 34 мс | 65 мс | 150 мс | 513 мс
Выбором | 4 мс | 26 мс | 65 мс | 125 мс | 330 мс | 1236 мс
Пузырьком | 16 мс | 58 мс | 155 мс | 360 мс | 1200 мс | 4725 мс

Величина элементов по модулю не влияет на быстродействие (ограничение в 3 знака было выбрано исходя из количества доступной памяти).

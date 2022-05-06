# Неделя 2. Жизненный цикл Fragment
## Теория

Изучим жизненный цикл Fragment и сравним его с ЖЦ Activity.

![](fragment_img/activity_n_fragment_lifecycle.png)

Основные методы ЖЦ Fragment:

Название | Когда вызывается | Особенности
--- | --- | ---
onAttach(Activity) | Когда фрагмент связывается с активностью. | С этого момента мы можем получить ссылку на активность через метод getActivity()
onCreate() | После onAttach() | В этом методе можно сделать работу, не связанную с интерфейсом. Например, подготовить адаптер.
onCreateView(LayoutInflater, ViewGroup, Bundle) | Один раз, когда фрагмент должен загрузить на экран свой интерфейс | Вызывается для создания компонентов внутри фрагмента
onViewCreated() | Сразу после onCreateView() | Можно обратиться к компонентам UI
onActivityCreated(Bundle) | Когда отработает метод активности onCreate() | **Deprecated** фрагмент может обратиться к компонентам активности
onStart() | После onCreateView()| Фрагмент видим для пользователя
onResume() | После onResume() активности | 
onPause() | После onPause() активности | 
onStop() | После onStop() активности | 
onDestroyView() | Когда набор компонентов удаляется из фрагмента | 
onDestroy() | После onDestroy() активности | 
onDetach() | Когда фрагмент отвязывается от активности | 

## Приложение

Приложение состоит из Activity и двух Fragment. Разметка Activity состоит из одного контейнера для фрагментов. Первый фрагмент содержит кнопку, по которой содержимое в контейнере меняется на второй фрагмент. Из второго фрагмента можно вернуться обратно на первый, либо открыть AlertDialog.

1. 

![](fragment_img/fragment_1.png)

2.

![](fragment_img/fragment_2.png)

3.

![](fragment_img/fragment_3.png)

4.

![](fragment_img/fragment_4.png)

5.

![](fragment_img/fragment_5.png)

## Вывод


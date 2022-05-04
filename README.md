# Wildberries стажировка
## Неделя 1: Манифест, основные компоненты Android-приложения, для решения каких задач их использовать
Всего есть 4 основных компонента Android-приложения.
Более подробное описание можно найти в комментарии в шапке соответствующего Activity.
+ Activity

An activity is a single, focused thing that the user can do. Almost all activities interact with the user, 
so the Activity class takes care of creating a window for you in which you can place your UI with setContentView(View).

В проекте используется для интерфейса выбора примера компонента и для создания самого интерфейса каждого из примеров.
+ Service

A Service is an application component that can perform long-running operations in the background. It does not provide a user interface. 
Once started, a service might continue running for some time, even after the user switches to another application. Additionally, 
a component can bind to a service to interact with it and even perform interprocess communication (IPC). For example, 
a service can handle network transactions, play music, perform file I/O, or interact with a content provider, all from the background.

В проекте используется для воспроизведения локального аудиофайла.
+ Broadcast receiver

Base class for code that receives and handles broadcast intents sent by Context.sendBroadcast(Intent).
You can either dynamically register an instance of this class with Context.registerReceiver() 
or statically declare an implementation with the <receiver> tag in your AndroidManifest.xml.

В проекте используется для реагирования на системное сообщение об изменении громкости.
+ Content provider

A content provider manages access to a central repository of data. A provider is part of an Android application, which often provides 
its own UI for working with the data. However, content providers are primarily intended to be used by other applications, 
which access the provider using a provider client object.
 
В проекте используется для предоставления доступа к SQL таблице.

# Неделя 6. Многопоточность, Handler
## Для чего нужен Handler? Что с помощью него можно делать? Как он работает?
Handler используется для оранизации межпотокового взаимодействия. Он управляет сообщениями, может отправить сообщение в другой поток, либо обработать сообщение из 
своего потока. К своему потоку он привязывается с помощью Looper'а. 

Сообщения могут содержать объекты Runnable, либо другие. Для выполнения сообщения добавляются в очередь сообщений потока, а из нее уже обрабатываются связанным(и) 
handler('ами). Добавить сообщения в очередь можно для послетовательного выполнения, для выполнения с задержкой и для отложенного выполнения.
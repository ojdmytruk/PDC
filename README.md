# Паралельні та розподілені обчислення
### 1. lab1 - Розробка потоків та дослідження пріоритету запуску потоків
  + [Pool](https://github.com/ojdmytruk/PDC/tree/master/lab1/src/Pool) - основа коду - By Cay S. Horstman, додані пріоритети та метод join
  + [Symbols](https://github.com/ojdmytruk/PDC/tree/master/lab1/src/Symbols) - почергове виведення символів на консоль (через wait notify)
  + [Counter](https://github.com/ojdmytruk/PDC/tree/master/lab1/src/Counter) - синхронізоване зменшення та збільшення лічильника: синхронізований метод, блок, локер
### 2. lab2 - Розробка паралельних алгоритмів множення матриць та дослідження їх ефективності
  + [BlockStripedAlgo](https://github.com/ojdmytruk/PDC/tree/master/lab2/src/BlockStripedAlgo) - Стрічковий алгоритм: в потоки передається повна копія матриці В, дає кращий SpeedUp
  + [BlockStripedCorrected](https://github.com/ojdmytruk/PDC/tree/master/lab2/src/BlockStripedCorrected) - Стрічковий алгоритм: потік викликається через run() замість start(), в потік циклічно передається стовпчик матриці B. Меньший SpeedUp, через run() все розраховується в main (некоректна робота з потоками)
  + [FoxAlgo](https://github.com/ojdmytruk/PDC/tree/master/lab2/src/FoxAlgo) - Алгоритм Фокса: реалізація через 4вимірний масив, через run() все розраховується в main (некоректна робота з потоками)
  + [FoxThread](https://github.com/ojdmytruk/PDC/tree/master/lab2/src/FoxThread) - Алгоритм Фокса: реалізація через 4вимірний масив
### 3. lab3 - Розробка паралельних програм з використанням механізмів синхронізації: синхронізовані методи, локери, спеціальні типи
  + [Bank](https://github.com/ojdmytruk/PDC/tree/master/lab3/src/Bank) - приклад Cay Horstmann, додано 3 методи синхронізації: блок, локер, wait notify
  + [ProducerConsumer](https://github.com/ojdmytruk/PDC/tree/master/lab3/src/ProducerConsumer) - приклад з [oracle](https://docs.oracle.com/javase/tutorial/essential/concurrency/guardmeth.html) , масив String змінено на int
  + [PrConBlocking](https://github.com/ojdmytruk/PDC/tree/master/lab3/src/PrConBlocking) - реалізація задачі Producer-Consumer через використання інтерфейсу BlockingQueue (в реалізаціїї SynchronousQueue)
  + [Journal](https://github.com/ojdmytruk/PDC/tree/master/lab3/src/Journal) - електронний журнал (викладачі - потоки)
### 4. lab4 - Розробка паралельних програм з використанням пулів потоків, екзекьюторів та ForkJoinFramework
  + [task1](https://github.com/ojdmytruk/PDC/tree/master/lab4/src/task1) - статистичний аналіз текстів з визначенням характеристик випадкової величини "довжина слова в символах"
  + [task2](https://github.com/ojdmytruk/PDC/tree/master/lab4/src/task2) - реалізація lab3 Journal через Fork/Join
  + [task3](https://github.com/ojdmytruk/PDC/tree/master/lab4/src/task3) - пошук спільних слів
  + [task4](https://github.com/ojdmytruk/PDC/tree/master/lab4/src/task4) - пошук текстових документів, що відповідають ключевим словам

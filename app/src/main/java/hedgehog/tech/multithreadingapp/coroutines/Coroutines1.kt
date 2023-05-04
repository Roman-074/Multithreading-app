package hedgehog.tech.multithreadingapp.coroutines

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import hedgehog.tech.multithreadingapp.R
import hedgehog.tech.multithreadingapp.databinding.Coroutines1Binding
import hedgehog.tech.multithreadingapp.main.AnimationUtils
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

/**
 - Виды билдеров
 - Переключение контекста
 - Suspend фнукции
 */

class Coroutines1 : AppCompatActivity(R.layout.coroutines_1) {

    private val viewBinding by viewBinding(Coroutines1Binding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.buttonLaunch.setOnClickListener { launchBuilder() }
        viewBinding.buttonAsync.setOnClickListener { asyncBuilder() }
        viewBinding.buttonRunblocking.setOnClickListener { runBlockingBuilder() }
        AnimationUtils.setupAnimation(viewBinding.lottieAnimation)
    }

    // launch больше похож на построитель сопрограмм типа «запустил и забыл»
    // Запускает новую сопрограмму без блокирования текущего потока, и возвращает ссылку
    // на сопрограмму вида Job
    private fun launchBuilder() {
        CoroutineScope(Dispatchers.IO).launch {
            longTask()
            withContext(Dispatchers.Main) {
                viewBinding.textStatus.text = "Скачивание завершено"
            }
        }
    }

    // async такой же как launch с тем исключением, что он возвращает Deferred<T>
    // Это ключевое различие между async и launch
    // Deferred<T> возвращает конкретное значение типа T после того, как сопрограмма завершает
    // выполнение, тогда как в Job этого не происходит
    // запустить async можно только изнутри другой корутины
    // еще пример: launch больше похож на построитель сопрограмм типа «запустил и забыл»,
    // в то время как async возвращает значение после того, как сопрограмма завершила выполнение
    private fun asyncBuilder() {
        CoroutineScope(Dispatchers.IO).launch {
            val deferred: Deferred<String> = async {
                longTask()
            }
            // ожидает завершения сопрограммы и возвращает результат
            val resultString: String = deferred.await()

            Log.d("my", "async fun $resultString ")
            // работа со view как мы помним доступна только из main потока. Нужно переключить диспетчер
            withContext(Dispatchers.Main) {
                viewBinding.textStatus.text = "Скачивание завершено"
            }
        }
//        GlobalScope.launch {
//            longTask()
//        }
    }

    // runBlocking запускает новую сопрограмму и блокирует текущий поток до ее завершения
    // Эту функцию нельзя использовать из сопрограммы. Она разработана, чтобы связать обычный
    // блокирующий код с библиотеками, написанными в стиле приостановки
    private fun runBlockingBuilder() {
        viewBinding.textStatus.text = "Старт блокировки"
        runBlocking {
            longTask()
        }
    }

    // чтобы переключать контекст потоков внутри функции, нужно пометить ее как suspend
    // suspend функции можно запускать только изнутри корутин
    // suspend - приостанавливает выполнение текущей сопрограммы, сохраняя все локальные переменные.
    // Текущий поток может продолжить свою работу, в то время как код приостановки
    // выполняется в другом потоке
    // !!! suspend функция не блокирует поток, а всего лишь приостанавливает сопрограмму
    private suspend fun longTask(): String {
//        val a = CoroutineScope(Dispatchers.IO).async {
//            longTask()
//        }.await()
        println("Click!")
        for (i in 0..7) {
            downloadFile(i)
            withContext(Dispatchers.Main) {
                viewBinding.textStatus.text = "Закачано файлов: $i"
            }
        }
        return "Success!"
    }

    private fun downloadFile(index: Int) {
        try {
//            longTask()
            TimeUnit.MILLISECONDS.sleep(300)
            println("Загрузка файла... $index")
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}

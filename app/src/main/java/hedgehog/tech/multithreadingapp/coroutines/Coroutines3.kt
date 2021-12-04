package hedgehog.tech.multithreadingapp.coroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import hedgehog.tech.multithreadingapp.R
import hedgehog.tech.multithreadingapp.databinding.Coroutines3Binding
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

class Coroutines3: AppCompatActivity(R.layout.coroutines_3), CoroutineScope {

    private val viewBinding by viewBinding(Coroutines3Binding::bind)

    // переменная, связанная с жизненным циклом активити
    lateinit var job: Job
    // делаем сочетание основного контекста с контекстом задания
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    // по аналогии
    private val ioContext: CoroutineContext
        get() = Dispatchers.IO + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // привязываем работу данной джобы к жизненному циклу активити
        job = Job()
        viewBinding.buttonStart.setOnClickListener {
            launchScope()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // не забываем отменять работу джобу при завершении работы с активити
        job.cancel()
    }

    // launch Builder запускает новую сопрограмму без блокирования текущего
    // потока, и возвращает ссылку на сопрограммы вида Job

    // (launch доступен тут потому, что мы унаследовали активити от CoroutineScope)
    private fun launchScope() = launch(ioContext) {
        longTask()
    }

    // чтобы переключать контекст потоков внутри функции, нужно пометить ее как suspend
    private suspend fun longTask(): String{
        println("Click!")
        for (i in 0..7){
            downloadFile(i)
            withContext(context = coroutineContext){
                viewBinding.textStatus.text = "Закачано файлов: $i"
            }
        }
        return "Success!"
    }

    private fun downloadFile(index: Int){
        try {
            TimeUnit.SECONDS.sleep(1)
            println("Загрузка файла... $index")
        } catch (ex: Exception){
            ex.printStackTrace()
        }
    }


    // async Builder такой же , как launch, с тем исключением , что он возвращает Deferred<T>
    // Это ключевое различие между async и launch

    // Deferred<T> возвращает конкретное значение типа T после того, как сопрограмма завершает
    // выполнение, тогда как в Job этого не происходит
    // еще пример: launch больше похож на построитель сопрограмм типа «запустил и забыл»,
    // в то время как async возвращает значение после того, как сопрограмма завершила выполнение

//        val deferred: Deferred<String> = async(context = ioContext) {
//            longTask()
//        }
//        // ожидает завершения сопрограммы и возвращает результат
//        val resultString: String = deferred.await()
//
//        Log.d("my", "async fun $resultString ")
//        // работа со view как мы помним доступна только из main потока. Нужно обязательно переключать контекст
//        withContext(context = coroutineContext){
//            viewBinding.textStatus.text = "Async fun заверишла работу и вернула значение"
//        }


}
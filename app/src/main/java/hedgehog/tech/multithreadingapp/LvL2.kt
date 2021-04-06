package hedgehog.tech.multithreadingapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import hedgehog.tech.multithreadingapp.databinding.Activity2Binding
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

class LvL2: AppCompatActivity(), CoroutineScope {

    lateinit var binding: Activity2Binding

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
        binding = Activity2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        // привязываем работу данной джобы к жизненному циклу активити
        job = Job()
        binding.buttonStart.setOnClickListener {
            launchScope()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        // не забываем отменять работу джлбы при завершении работы с активити
        job.cancel()
    }

    // launch Builder запускает новую сопрограмму без блокирования текущего
    // потока, и возвращает ссылку на сопрограммы вида Job

    // async Builder такой же , как launch, с тем исключением , что он возвращает Deferred<T>
    // Это ключевое различие между async и launch

    // Deferred<T> возвращает конкретное значение типа T после того, как сопрограмма завершает
    // выполнение, тогда как в Job этого не происходит
    // еще пример: launch больше похож на построитель сопрограмм типа «запустил и забыл»,
    // в то время как async возвращает значение после того, как сопрограмма завершила выполнение

    // (launch доступен тут потому, что мы унаследовали активити от CoroutineScope)
    private fun launchScope() = launch {
        val deferred: Deferred<String> = async(context = ioContext) {
            longTask()
        }
        // ожидает завершения сопрограммы и возвращает результат
        val resultString: String = deferred.await()

        Log.d("my", "async fun $resultString ")
        // работа со view как мы помним доступна только из main потока. Нужно обязательно переключать контекст
        withContext(context = coroutineContext){
            binding.textStatus.text = "Async fun заверишла работу и вернула значение"
        }
    }

    // чтобы переключать контекст потоков внутри функции, нужно пометить ее как suspend
    private suspend fun longTask(): String{
        println("Click!")
        for (i in 0..7){
            downloadFile(i)
            withContext(context = coroutineContext){
                binding.textStatus.text = "Закачано файлов: $i"
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

}
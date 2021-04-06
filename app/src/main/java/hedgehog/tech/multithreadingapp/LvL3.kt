package hedgehog.tech.multithreadingapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import hedgehog.tech.multithreadingapp.databinding.Activity3Binding
import kotlinx.coroutines.*

class LvL3: AppCompatActivity() {

    lateinit var binding: Activity3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Activity3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonJoin.setOnClickListener {
            runBlocking {
                initJob().join()
                Log.d("my", "Main block >>>")
                binding.textStatus.text = "Main block >>>"
            }
        }

        binding.buttonStart.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                initJob().join()
                Log.d("my", "Background block >>>")
                binding.textStatus.text = "Background block >>>"
            }
        }


        binding.buttonCancel.setOnClickListener {
            val ex = initJob()
            runBlocking {
                delay(3000)
                ex.cancel()
                Log.d("my", "Cancel >>>")
                binding.textStatus.text = "Cancel >>>"
            }
        }

    }

    private fun initJob(): Job {
        return GlobalScope.launch(Dispatchers.IO) {
            repeat(5) {
                Log.d("my", "tick $it")
                delay(1000)
            }
        }
    }


    // runBlocking запускает новую сопрограмму и блокирует текущий поток до его завершения
    // Эту функцию нельзя использовать из сопрограммы. Она разработана, чтобы связать обычный
    // блокирующий код с библиотеками, написанными в стиле приостановки

    // Функция join () - это функция приостановки, то есть ее можно вызвать из сопрограммы
    // или из другой suspend функции. Она блокирует все потоки до тех пор, пока
    // сопрограмма, в которой она написана, или контекст не завершит свою работу. Только когда
    // сопрограмма завершит свою работу, будут выполнены строки после функции join ()

    // cancel () используется для отмены сопрограммы, не дожидаясь ее завершения. Можно сказать,
    // что это прямо противоположно методу join() в том смысле, что метод join () ожидает, пока
    // сопрограмма завершит всю свою работу и заблокирует все другие потоки, тогда как метод cancel()
    // при обнаружении убивает coroutine, т.е. останавливает сопрограмму

}
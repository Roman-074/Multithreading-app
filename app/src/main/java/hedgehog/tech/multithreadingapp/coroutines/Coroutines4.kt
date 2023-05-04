package hedgehog.tech.multithreadingapp.coroutines

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import hedgehog.tech.multithreadingapp.R
import hedgehog.tech.multithreadingapp.databinding.Coroutines4Binding
import hedgehog.tech.multithreadingapp.main.AnimationUtils
import kotlinx.coroutines.*

/**
 - Состояние корутины, отмена
 - Join
 */

class Coroutines4 : AppCompatActivity(R.layout.coroutines_4) {

    private val viewBinding by viewBinding(Coroutines4Binding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AnimationUtils.setupAnimation(viewBinding.lottieAnimation)

        var exJob: Job? = null

        viewBinding.buttonStart.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                exJob = CoroutineScope(Dispatchers.IO).launch {
                    repeat(5) {
                        viewBinding.textStatus.text = "tick $it"
                        Log.d("my", "tick $it")
                        delay(700)
                    }
                }
                exJob?.join()
                viewBinding.textStatus.text = "Задача завершена"
            }
        }

        viewBinding.buttonCancel.setOnClickListener {
            exJob?.let {
                if (it.isActive) {
                    it.cancel()
                }
            }
            Log.d("my", "Задача отменена >>>")
            viewBinding.textStatus.text = "Задача отменена"
        }

        viewBinding.buttonJoin.setOnClickListener {
            viewBinding.textStatus.text = "Задача запущена"
            runBlocking {
                Log.d("my", "Старт join1 >>>")
                initJob().join()
                Log.d("my", "Старт join2 >>>")
                initJob2().join()
                Log.d("my", "UI поток восстановлен >>>")
                viewBinding.textStatus.text = "UI поток восстановлен"
            }
        }
    }

    private fun initJob(): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            repeat(5) {
                viewBinding.textStatus.text = "tick $it"
                Log.d("my", "tick $it")
                delay(700)
            }
        }
    }
    private suspend fun initJob2(): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            repeat(5) {
                viewBinding.textStatus.text = "tick2 $it"
                Log.d("my", "tick2 $it")
                delay(700)
            }
        }
    }

    // runBlocking запускает новую сопрограмму и блокирует текущий поток до его завершения
    // Эту функцию нельзя использовать из сопрограммы. Она разработана, чтобы связать обычный
    // блокирующий код с библиотеками, написанными в стиле приостановки

    // cancel () используется для отмены сопрограммы, не дожидаясь ее завершения. Можно сказать,
    // что это прямо противоположно методу join() в том смысле, что метод join () ожидает, пока
    // сопрограмма завершит всю свою работу и блокирует все другие потоки, тогда как метод cancel()
    // при обнаружении убивает coroutine, т.е. останавливает сопрограмму

    // Функция join () - это функция приостановки, то есть ее можно вызвать из сопрограммы
    // или из другой suspend функции. Она блокирует все потоки до тех пор, пока
    // сопрограмма, в которой она написана, или контекст не завершит свою работу. Только когда
    // сопрограмма завершит свою работу, будут выполнены строки после функции join ()
}

package hedgehog.tech.multithreadingapp.flow

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import hedgehog.tech.multithreadingapp.R
import hedgehog.tech.multithreadingapp.databinding.Flow0Binding
import hedgehog.tech.multithreadingapp.main.AnimationUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class Flow0 : AppCompatActivity(R.layout.flow_0) {

    private val viewBinding by viewBinding(Flow0Binding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AnimationUtils.setupAnimation(viewBinding.lottieAnimation)
        viewBinding.buttonStart.setOnClickListener {
            collectFlow()
        }
    }

    // Flow являются холодными источниками данных
    // Наиболее распространенным терминальным оператором является collect
    private fun collectFlow() {
        CoroutineScope(Dispatchers.IO).launch {
            numbersFlow().collect { value ->
                Log.d("my", "collectFlow: $value")
            }
        }
    }
    // !!! Важно помнить, что все операторы терминала приостанавливают функции и поэтому
    // должны вызываться из сопрограммы. Также все эти операторы приостанавливают
    // сопрограмму до тех пор, пока поток не будет полностью собран

    // Стандартная библиотека предоставляет несколько способов создания потока,
    // самый простой способ — использовать оператор потока
    private fun numbersFlow(): Flow<Int> = flow {
        // код внутри потока может быть приостановлен!
        for (i in 1..7) {
            Log.d("my", "before delay >>>")
            delay(500)
            Log.d("my", "after delay >>>")
            emit(i)
        }
    } // сама функция numberFlow() не приостанавливается

    // Существуют и другие способы создания Flow, включая функцию flowOf() и расширение asFlow() ,
    // которое можно использовать для коллекций, последовательностей,
    // диапазонов, значений или функциональных типов:
    private fun createOtherFlow() {
        flowOf(1, 2, 3)
        listOf("A", "B", "C").asFlow()
        (1..3).asFlow()
    }
}

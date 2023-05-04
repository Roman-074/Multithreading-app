package hedgehog.tech.multithreadingapp.flow

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import hedgehog.tech.multithreadingapp.R
import hedgehog.tech.multithreadingapp.databinding.Flow0Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class Flow1 : AppCompatActivity(R.layout.flow_0) {

    private val viewBinding by viewBinding(Flow0Binding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        numbersFlow()
    }

    // Стандартная библиотека предоставляет несколько способов создания потока,
    // самый простой способ — использовать оператор потока
    private fun numbersFlow(): Flow<Int> = flow {
        // код внутри потока может быть приостановлен!
        for (i in 1..3) {
            delay(100)
            Log.d("my", "numbersFlow: EEEEEEEE")
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

    // Потоки являются холодными, что означает, что код внутри построителя потоков
    // не выполняется до тех пор, пока к потоку не будет применен оператор терминала.
    // Наиболее распространенным терминальным оператором является collect
    private fun collectFlow() {
        CoroutineScope(Dispatchers.IO).launch {
            numbersFlow().collect { value ->
                Log.d("my", "collectFlow: $value")
            }
        }
    }
}

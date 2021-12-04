package hedgehog.tech.multithreadingapp.coroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import hedgehog.tech.multithreadingapp.R
import hedgehog.tech.multithreadingapp.databinding.Coroutines7Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class Coroutines7: AppCompatActivity(R.layout.coroutines_7) {

    private val viewBinding by viewBinding(Coroutines7Binding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.buttonStart.setOnClickListener {
            // при каждом клике создается отдельная корутина, которая работает изолированно от другой
            newLaunch()
        }
    }

    private fun newLaunch(){
        // внутри корутины операции привязаны к разным потокам, указанным в диспетчерах
        // для программиста код выглядит последовательным - пока не завершится выполнение
        // инструкции, следующая не запускается
        GlobalScope.launch {
            for (i in 0..7){
                withContext(Dispatchers.IO){
                    downloadFile(i)
                }
                withContext(Dispatchers.Main){
                    viewBinding.textStatus.text = "Закачано файлов: $i"
                }
            }
            viewBinding.textStatus.text = "Success!"
        }
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
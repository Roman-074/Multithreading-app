package hedgehog.tech.multithreadingapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hedgehog.tech.multithreadingapp.databinding.Activity1Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class LvL1: AppCompatActivity() {

    lateinit var binding: Activity1Binding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Activity1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonStart.setOnClickListener {
            // при каждом клике создается отдельная корутина, которая работает изолированно от другой
            newLaunch()
        }

        // тут создается сразу две корутины, которые работают параллельно
        binding.buttonDouble.setOnClickListener {
            newLaunch()
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
                    binding.textStatus.text = "Закачано файлов: $i"
                }
            }
            binding.textStatus.text = "Success!"
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
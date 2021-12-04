package hedgehog.tech.multithreadingapp.coroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import hedgehog.tech.multithreadingapp.R
import hedgehog.tech.multithreadingapp.databinding.Coroutines0Binding
import java.util.concurrent.TimeUnit

class Coroutines0 : AppCompatActivity(R.layout.coroutines_0) {

    private val viewBinding by viewBinding(Coroutines0Binding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding.buttonStart.setOnClickListener {
            // тут приложение виснет на долгой задаче (тестить лучше на API = 24 и ниже)
            longTask()
        }

    }

    private fun longTask(){
        println("Click!")
        for (i in 0..7){
            downloadFile(i)
//            viewBinding.textStatus.text = "Закачано файлов: $i"
            viewBinding.textStatus.text = "Закачано файлов: $i"
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
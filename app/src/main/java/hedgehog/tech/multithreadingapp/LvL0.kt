package hedgehog.tech.multithreadingapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hedgehog.tech.multithreadingapp.databinding.Activity0Binding
import java.util.concurrent.TimeUnit

class LvL0 : AppCompatActivity() {

    lateinit var binding: Activity0Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Activity0Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonStart.setOnClickListener {
            // тут приложение виснет на долгой задаче (тестить лучше на API = 24 и ниже)
            longTask()
        }

    }

    private fun longTask(){
        println("Click!")
        for (i in 0..7){
            downloadFile(i)
            binding.textStatus.text = "Закачано файлов: $i"
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
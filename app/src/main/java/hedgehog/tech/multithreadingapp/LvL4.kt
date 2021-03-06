package hedgehog.tech.multithreadingapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import hedgehog.tech.multithreadingapp.databinding.Activity4Binding
import kotlinx.coroutines.*

class LvL4: AppCompatActivity() {

    lateinit var binding: Activity4Binding
    lateinit var ex: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Activity4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonStartJob.setOnClickListener {
            ex = initJob()
        }

        binding.buttonCancel.setOnClickListener {
            runBlocking {
//                delay(2000)
                ex.cancel()
                Log.d("my", "Cancel >>>")
                binding.textStatus.text = "Cancel >>>"
            }
        }

        binding.buttonTimeout.setOnClickListener {
            initJobTimeout()
        }


    }

    private fun initJob(): Job {
        return GlobalScope.launch(Dispatchers.IO) {
            repeat(10000) {
                if (isActive) Log.d("my", "tick $it")
                else Log.d("my", "Finish!")
            }
        }
    }

    private fun initJobTimeout(): Job {
        return GlobalScope.launch(Dispatchers.IO) {
            withTimeout(1000){
                repeat(1000000) {
                    if (isActive) Log.d("my", "tick $it")
                    else Log.d("my", "Finish!")
                }
                Log.d("my", "End timeout >>>>>>")
            }

        }
    }


}
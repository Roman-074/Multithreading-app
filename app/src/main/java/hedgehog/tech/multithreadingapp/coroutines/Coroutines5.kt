package hedgehog.tech.multithreadingapp.coroutines

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import hedgehog.tech.multithreadingapp.R
import hedgehog.tech.multithreadingapp.databinding.Coroutines5Binding
import kotlinx.coroutines.*

class Coroutines5: AppCompatActivity(R.layout.coroutines_5) {

    private val viewBinding by viewBinding(Coroutines5Binding::bind)
    lateinit var ex: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding.buttonStartJob.setOnClickListener {
            ex = initJob()
        }

        viewBinding.buttonCancel.setOnClickListener {
            runBlocking {
//                delay(2000)
                ex.cancel()
                Log.d("my", "Cancel >>>")
                viewBinding.textStatus.text = "Cancel >>>"
            }
        }

        viewBinding.buttonTimeout.setOnClickListener {
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
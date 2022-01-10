package hedgehog.tech.multithreadingapp.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import hedgehog.tech.multithreadingapp.R
import hedgehog.tech.multithreadingapp.rxjava.RxJava
import hedgehog.tech.multithreadingapp.coroutines.*
import hedgehog.tech.multithreadingapp.databinding.ActivityMainBinding
import hedgehog.tech.multithreadingapp.flow.Flow0

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewBinding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding.apply {
            button0.setOnClickListener {
                startActivity(Intent(this@MainActivity, Coroutines0::class.java))
            }
            button1.setOnClickListener {
                startActivity(Intent(this@MainActivity, Coroutines1::class.java))
            }
            button2.setOnClickListener {
                startActivity(Intent(this@MainActivity, Coroutines3::class.java))
            }
            button3.setOnClickListener {
                startActivity(Intent(this@MainActivity, Coroutines4::class.java))
            }
            button4.setOnClickListener {
                startActivity(Intent(this@MainActivity, Coroutines5::class.java))
            }
            buttonFlow0.setOnClickListener {
                startActivity(Intent(this@MainActivity, Flow0::class.java))
            }
            buttonRx.setOnClickListener {
                startActivity(Intent(this@MainActivity, RxJava::class.java))
            }
        }


    }

}
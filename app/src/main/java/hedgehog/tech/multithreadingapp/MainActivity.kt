package hedgehog.tech.multithreadingapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import hedgehog.tech.multithreadingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewBinding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding.apply {
            button0.setOnClickListener {
                startActivity(Intent(this@MainActivity, LvL0::class.java))
            }
            button1.setOnClickListener {
                startActivity(Intent(this@MainActivity, LvL1::class.java))
            }
            button2.setOnClickListener {
                startActivity(Intent(this@MainActivity, LvL2::class.java))
            }
            button3.setOnClickListener {
                startActivity(Intent(this@MainActivity, LvL3::class.java))
            }
            button4.setOnClickListener {
                startActivity(Intent(this@MainActivity, LvL4::class.java))
            }
            buttonRx.setOnClickListener {
                startActivity(Intent(this@MainActivity, RxJava::class.java))
            }
        }


    }

}
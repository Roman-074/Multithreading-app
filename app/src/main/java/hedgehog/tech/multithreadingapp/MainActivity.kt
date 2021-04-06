package hedgehog.tech.multithreadingapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hedgehog.tech.multithreadingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button0.setOnClickListener { startActivity(Intent(this, LvL0::class.java)) }
        binding.button1.setOnClickListener { startActivity(Intent(this, LvL1::class.java)) }
        binding.button2.setOnClickListener { startActivity(Intent(this, LvL2::class.java)) }
        binding.button3.setOnClickListener { startActivity(Intent(this, LvL3::class.java)) }
        binding.button4.setOnClickListener { startActivity(Intent(this, LvL4::class.java)) }

        binding.buttonRx.setOnClickListener { startActivity(Intent(this, RxJava::class.java)) }

    }

}
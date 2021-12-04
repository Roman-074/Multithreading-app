package hedgehog.tech.multithreadingapp.livedata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import hedgehog.tech.multithreadingapp.R
import hedgehog.tech.multithreadingapp.databinding.Livedata0Binding

class LiveData0 : AppCompatActivity(R.layout.livedata_0) {

    private val viewBinding by viewBinding(Livedata0Binding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}
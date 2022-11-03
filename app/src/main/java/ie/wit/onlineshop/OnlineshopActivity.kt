package ie.wit.onlineshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.wit.onlineshop.databinding.ActivityOnlineshopBinding
import timber.log.Timber
import timber.log.Timber.i

class OnlineshopActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnlineshopBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnlineshopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        i("Onlineshop Activity started...")

        binding.btnAdd.setOnClickListener() {
            i("add Button Pressed")
            val productName = binding.productName.text.toString()
            if (productName.isNotEmpty()) {
                i("add Button Pressed: $productName")
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

    }
}







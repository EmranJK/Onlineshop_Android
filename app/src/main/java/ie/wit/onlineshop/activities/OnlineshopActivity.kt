package ie.wit.onlineshop.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.wit.onlineshop.databinding.ActivityOnlineshopBinding
import ie.wit.onlineshop.models.OnlineshopModel
import timber.log.Timber
import timber.log.Timber.i

class OnlineshopActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnlineshopBinding
    var product = OnlineshopModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnlineshopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        i("Onlineshop Activity started...")

        binding.btnAdd.setOnClickListener() {
            product.name = binding.productName.text.toString()
            if (product.name.isNotEmpty()) {
                i("add Button Pressed: $product.name")
            }
            else {
                Snackbar
                    .make(it,"Please Enter a Product Name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}
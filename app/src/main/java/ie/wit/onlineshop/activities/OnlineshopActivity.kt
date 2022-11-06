package ie.wit.onlineshop.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.wit.onlineshop.databinding.ActivityOnlineshopBinding
import ie.wit.onlineshop.main.MainApp
import ie.wit.onlineshop.models.OnlineshopModel
import timber.log.Timber
import timber.log.Timber.i

class OnlineshopActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnlineshopBinding
    var product = OnlineshopModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnlineshopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Onlineshop Activity started...")
        binding.btnAdd.setOnClickListener() {
            product.name = binding.productName.text.toString()
            product.id = binding.productId.text.toString().toInt()
            if (product.name.isNotEmpty() && product.id.toString().isNotEmpty()) {
            // if (product.name.isNotEmpty()) {
                app.products.add(product.copy())
                i("add Button Pressed: ${product}")
                for (i in app.products.indices) {
                    i("Product[$i]:${this.app.products[i]}")
                }
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar.make(it,"Please Fill All Required Text Fields", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}
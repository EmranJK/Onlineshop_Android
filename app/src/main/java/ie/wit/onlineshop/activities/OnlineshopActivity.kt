package ie.wit.onlineshop.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import ie.wit.onlineshop.R
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

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        i("Onlineshop Activity started...")

        val ptypes = resources.getStringArray(R.array.productTypes)
        val spinner = findViewById<Spinner>(R.id.productType)
        var chosenProductType = ""
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, ptypes)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    chosenProductType = ptypes[position]
                    Toast.makeText(this@OnlineshopActivity,
                                "" + ptypes[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        if (intent.hasExtra("product_edit")) {
            product = intent.extras?.getParcelable("product_edit")!!
            binding.productName.setText(product.name)
            binding.productBrand.setText(product.brand)
            //binding.productType.setText(product.type)
            binding.productType.setSelection(ptypes.indexOf(product.type))
            //binding.productId.setText(product.id)
            binding.productPrice.setText(product.price.toString())

        }

        binding.btnAdd.setOnClickListener() {
            product.name = binding.productName.text.toString()
            product.price = binding.productPrice.text.toString().toDouble()
            product.brand = binding.productBrand.text.toString()
            //product.type = binding.productType.text.toString()
            product.type = chosenProductType
            if (product.name.isNotEmpty()) {
                app.products.create(product.copy())
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar.make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_product, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
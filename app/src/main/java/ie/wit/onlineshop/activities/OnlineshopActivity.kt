package ie.wit.onlineshop.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.wit.onlineshop.R
import ie.wit.onlineshop.databinding.ActivityOnlineshopBinding
import ie.wit.onlineshop.helpers.showImagePicker
import ie.wit.onlineshop.main.MainApp
import ie.wit.onlineshop.models.Location
import ie.wit.onlineshop.models.OnlineshopModel
import timber.log.Timber
import timber.log.Timber.i

class OnlineshopActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnlineshopBinding
    var product = OnlineshopModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    //var location = Location(52.245696, -7.139102, 15f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false
        binding = ActivityOnlineshopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerImagePickerCallback()
        registerMapCallback()

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
            edit = true
            product = intent.extras?.getParcelable("product_edit")!!
            binding.productName.setText(product.name)
            binding.productBrand.setText(product.brand)
            //binding.productType.setText(product.type)
            binding.productType.setSelection(ptypes.indexOf(product.type))
            //binding.productId.setText(product.id)
            binding.productPrice.setText(product.price.toString())
            binding.btnAdd.setText(R.string.save_product)

            Picasso.get()
                .load(product.image)
                .into(binding.productImage)
            if (product.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_product_image)
            }
        }


        binding.providerLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (product.zoom != 0f) {
                location.lat =  product.providerLat
                location.lng = product.providerLng
                location.zoom = product.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        binding.btnAdd.setOnClickListener() {
            product.name = binding.productName.text.toString()
            product.price = binding.productPrice.text.toString().toDouble()
            product.brand = binding.productBrand.text.toString()
            //product.type = binding.productType.text.toString()
            product.type = chosenProductType
            if (product.name.isEmpty()) {
                Snackbar.make(it,R.string.enter_product_name, Snackbar.LENGTH_LONG)
                    .show()
            }
            else {
                if (edit) {
                    app.products.update(product.copy())
                } else {
                    app.products.create(product.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
        }
        binding.chooseImage.setOnClickListener {
            //i("Select image")
            showImagePicker(imageIntentLauncher)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_product, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            product.image = result.data!!.data!!
                            Picasso.get()
                                .load(product.image)
                                .into(binding.productImage)
                            binding.chooseImage.setText(R.string.change_product_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            product.providerLat = location.lat
                            product.providerLng = location.lng
                            product.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
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
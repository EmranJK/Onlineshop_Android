package ie.wit.onlineshop.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.wit.onlineshop.R
import ie.wit.onlineshop.adapters.OnlineshopAdapter
import ie.wit.onlineshop.adapters.OnlineshopListener
import ie.wit.onlineshop.databinding.ActivityProductListBinding
import ie.wit.onlineshop.databinding.CardProductBinding
import ie.wit.onlineshop.main.MainApp
import ie.wit.onlineshop.models.OnlineshopModel

class ProductListActivity : AppCompatActivity(), OnlineshopListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityProductListBinding
    lateinit var searchFilter: SearchView
    lateinit var adapter: OnlineshopAdapter
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        searchFilter = findViewById(R.id.searchProduct)


        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
//        // on below line we are adding on query
//        // listener for our search view.
//        searchFilter.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                // on below line we are checking
//                // if query exist or not.
//
//                return false
//            }
//            override fun onQueryTextChange(newText: String?): Boolean {
//                // if query text is change in that case we
//                // are filtering our adapter with
//                // new text on below line.
//                val str:String
//                if(newText.isNullOrBlank())
//                    str = ""
//                else str = newText
//                //binding.recyclerView.adapter =)
//                return true
//            }
//        })

        searchFilter.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
        //binding.recyclerView.adapter = OnlineshopAdapter(app.products)
        adapter = OnlineshopAdapter(app.products.findAll(),this)
        binding.recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, OnlineshopActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onProductClick(product: OnlineshopModel, pos : Int) {
        val launcherIntent = Intent(this, OnlineshopActivity::class.java)
        launcherIntent.putExtra("product_edit", product)
        position = pos
        getClickResult.launch(launcherIntent)
    }

    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.products.findAll().size)
            }
            else // Deleting
                if (it.resultCode == 99)
                        (binding.recyclerView.adapter)?.notifyItemRemoved(position)
        }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                //notifyItemRangeChanged(0,app.products.size)
                notifyItemRangeChanged(0,app.products.findAll().size)
            }
        }


    }


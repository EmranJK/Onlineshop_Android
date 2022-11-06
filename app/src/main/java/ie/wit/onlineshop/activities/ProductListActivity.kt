package ie.wit.onlineshop.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.wit.onlineshop.R
import ie.wit.onlineshop.databinding.ActivityProductListBinding
import ie.wit.onlineshop.databinding.CardProductBinding
import ie.wit.onlineshop.main.MainApp
import ie.wit.onlineshop.models.OnlineshopModel

class ProductListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityProductListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = OnlineshopAdapter(app.products)
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

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.products.size)
            }
        }


    }

class OnlineshopAdapter constructor(private var products: List<OnlineshopModel>) :
    RecyclerView.Adapter<OnlineshopAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardProductBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val product = products[holder.adapterPosition]
        holder.bind(product)
    }

    override fun getItemCount(): Int = products.size

    class MainHolder(private val binding : CardProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: OnlineshopModel) {
            binding.productName.text = product.name
            binding.description.text = product.id.toString()
        }
    }
}
package ie.wit.onlineshop.main

import android.app.Application
import ie.wit.onlineshop.models.OnlineshopJSONStore
import ie.wit.onlineshop.models.OnlineshopMemStore
import ie.wit.onlineshop.models.OnlineshopStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application(){

    //val products = ArrayList<OnlineshopModel>()
    //val products = OnlineshopMemStore()

    lateinit var products: OnlineshopStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        //products = OnlineshopMemStore()
        products = OnlineshopJSONStore(applicationContext)
        i("Onlineshop started")
//        products.add(OnlineshopModel("Apple", 1))
//        products.add(OnlineshopModel("Orange", 2))
//        products.add(OnlineshopModel("Bannana", 3))
    }

}
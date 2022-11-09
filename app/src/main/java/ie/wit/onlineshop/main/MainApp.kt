package ie.wit.onlineshop.main

import android.app.Application
import ie.wit.onlineshop.models.OnlineshopMemStore
import ie.wit.onlineshop.models.OnlineshopModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application(){

    //val products = ArrayList<OnlineshopModel>()
    val products = OnlineshopMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Onlineshop started")
//        products.add(OnlineshopModel("Apple", 1))
//        products.add(OnlineshopModel("Orange", 2))
//        products.add(OnlineshopModel("Bannana", 3))
    }

}
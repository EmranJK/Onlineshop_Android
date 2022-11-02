package ie.wit.onlineshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import timber.log.Timber
import timber.log.Timber.i

class OnlineshopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onlineshop)

        Timber.plant(Timber.DebugTree())
        i("Onlineshop Activity started...")
    }
}
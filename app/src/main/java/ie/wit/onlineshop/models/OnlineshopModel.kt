package ie.wit.onlineshop.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OnlineshopModel(var id: Long = 0,
                           var name: String = "",
                           var price: Double = 0.0,
                           var brand: String = "",
                           var type: String = "",
                           var image: Uri = Uri.EMPTY) : Parcelable

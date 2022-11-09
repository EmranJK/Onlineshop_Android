package ie.wit.onlineshop.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OnlineshopModel(var id: Long = 0,
                           var name: String = "",
                           var price: Double = 0.0) : Parcelable

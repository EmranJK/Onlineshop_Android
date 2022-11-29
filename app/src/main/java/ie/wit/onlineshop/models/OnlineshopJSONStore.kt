package ie.wit.onlineshop.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import ie.wit.onlineshop.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "onlineshop.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<OnlineshopModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class OnlineshopJSONStore(private val context: Context) : OnlineshopStore {

    var products = mutableListOf<OnlineshopModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<OnlineshopModel> {
        logAll()
        return products
    }

    override fun create(product: OnlineshopModel) {
        product.id = generateRandomId()
        products.add(product)
        serialize()
    }


    override fun update(product: OnlineshopModel) {
        val productsList = findAll() as ArrayList<OnlineshopModel>
        var foundProduct: OnlineshopModel? = productsList.find { p -> p.id == product.id }
        if (foundProduct != null) {
            foundProduct.name = product.name
            foundProduct.price = product.price
            foundProduct.brand = product.brand
            foundProduct.type = product.type
            foundProduct.image = product.image
            foundProduct.providerLat = product.providerLat
            foundProduct.providerLng = product.providerLng
            foundProduct.zoom = product.zoom
            logAll()
        }
        serialize()
    }

    override fun delete(product: OnlineshopModel) {
        products.remove(product)
        serialize()
    }


    override fun searchFilter(name: String): List<OnlineshopModel> {
        return products.filter { p -> p.name.contains(name) }
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(products, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        products = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        products.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}
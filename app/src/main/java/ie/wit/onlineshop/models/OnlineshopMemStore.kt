package ie.wit.onlineshop.models
import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class OnlineshopMemStore : OnlineshopStore {

    val products = ArrayList<OnlineshopModel>()

    override fun findAll(): List<OnlineshopModel> {
        return products
    }

    override fun create(product: OnlineshopModel) {
        product.id = getId()
        products.add(product)
        logAll()
    }

    override fun update(product: OnlineshopModel) {
        var foundProduct: OnlineshopModel? = products.find { p -> p.id == product.id }
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
    }

    override fun delete(product: OnlineshopModel) {
        products.remove(product)
    }

    override fun searchFilter(name: String) : List<OnlineshopModel> {
        return products.filter { p -> p.name.contains(name) }
    }

    fun logAll() {
        products.forEach{ i("${it}") }
    }
}
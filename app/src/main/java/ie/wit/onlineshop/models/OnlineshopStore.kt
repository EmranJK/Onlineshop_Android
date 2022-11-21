package ie.wit.onlineshop.models

interface OnlineshopStore {
    fun findAll(): List<OnlineshopModel>
    fun create(product: OnlineshopModel)
    fun update(product: OnlineshopModel)
    fun searchFilter(name: String): List<OnlineshopModel>
}
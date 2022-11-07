package ie.wit.onlineshop.models

interface OnlineshopStore {
    fun findAll(): List<OnlineshopModel>
    fun create(product: OnlineshopModel)
}
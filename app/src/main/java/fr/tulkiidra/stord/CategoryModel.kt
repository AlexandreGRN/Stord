package fr.tulkiidra.stord

class CategoryModel (
    var id: Int = 123456789,
    var name: String = "Undefined",
    var description: String = "Undefined",
    var imageURL: String = "https://cdn.pixabay.com/photo/2014/04/10/17/59/doubt-321144_960_720.png",
    var favorite: Boolean = false,
    var owner_id: Int = 1,
    var parent_category_id: Int = 0
)

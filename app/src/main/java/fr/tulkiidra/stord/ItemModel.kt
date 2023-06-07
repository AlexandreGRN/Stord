package fr.tulkiidra.stord

class ItemModel (
    var id: Int = 123456789,
    var name: String = "Undefined",
    var description: String = "Undefined",
    var remaining: Int = 0,
    var alert: Int = 0,
    var imageURL: String = "https://cdn.pixabay.com/photo/2014/04/10/17/59/doubt-321144_960_720.png",
    var favorite: Boolean = false,
    var parent_category_id: Int = 0,
)

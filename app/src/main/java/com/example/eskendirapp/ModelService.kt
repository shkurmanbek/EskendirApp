package com.example.eskendirapp

class ModelService {

    var id:String = ""
    var title1:String = ""
    var title2:String = ""
    var image:String = ""
    var typeService:Int = 0

    constructor()

    constructor(id: String, title1: String, title2: String, image: String, typeService: Int) {
        this.id = id
        this.title1 = title1
        this.title2 = title2
        this.image = image
        this.typeService = typeService
    }
}
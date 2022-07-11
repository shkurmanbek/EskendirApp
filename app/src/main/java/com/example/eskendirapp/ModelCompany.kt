package com.example.eskendirapp

class ModelCompany {
    var id:String = ""
    var title1:String = ""
    var title2:String = ""
    var mainIv:String = ""
    var starIv:String = ""
    var price:Int = 0
    var type:Int = 0

    constructor()

    constructor(id: String, title1: String, title2: String, mainIv: String, starIv: String, price: Int, type: Int) {
        this.id = id
        this.title1 = title1
        this.title2 = title2
        this.mainIv = mainIv
        this.starIv = starIv
        this.price = price
        this.type = type
    }
}
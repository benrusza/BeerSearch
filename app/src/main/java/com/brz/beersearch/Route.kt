package com.brz.beersearch

sealed class Route(val route: String) {
    object MainScreen: Route ("MainScreen")
    object BeerDetail: Route ("BeerDetail/{id}")


    fun withId(id : String) : String{
        return BeerDetail.route.replace("{id}",id)
    }
}
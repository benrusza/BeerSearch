package com.brz.beersearch.model

data class Ingredients(
    val hops: List<Any>,
    val malt: List<Malt>,
    val yeast: String
)
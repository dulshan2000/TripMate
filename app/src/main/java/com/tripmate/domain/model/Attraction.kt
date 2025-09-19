package com.tripmate.domain.model
data class Attraction(
  val id:String, val name:String, val location:String, val imageUrl:String,
  val priceTier:Int, val rating:Double, val reviews:Int, val tags:List<String>
)

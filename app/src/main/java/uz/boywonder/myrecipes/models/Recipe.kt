package uz.boywonder.myrecipes.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Recipe(
    @Json(name = "results")
    val results: List<Result>
)
package uz.boywonder.myrecipes.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "aggregateLikes")
    val aggregateLikes: Int? = null,
    @Json(name = "cheap")
    val cheap: Boolean = false,
    @Json(name = "dairyFree")
    val dairyFree: Boolean = false,
    @Json(name = "extendedIngredients")
    val extendedIngredients: List<ExtendedIngredient>? = null,
    @Json(name = "glutenFree")
    val glutenFree: Boolean = false,
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "image")
    val image: String? = null,
    @Json(name = "readyInMinutes")
    val readyInMinutes: Int? = null,
    @Json(name = "sourceName")
    val sourceName: String? = null,
    @Json(name = "sourceUrl")
    val sourceUrl: String? = null,
    @Json(name = "summary")
    val summary: String? = null,
    @Json(name = "title")
    val title: String? = null,
    @Json(name = "vegan")
    val vegan: Boolean = false,
    @Json(name = "vegetarian")
    val vegetarian: Boolean = false,
    @Json(name = "veryHealthy")
    val veryHealthy: Boolean = false,
)
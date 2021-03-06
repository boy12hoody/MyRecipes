package uz.boywonder.myrecipes.models


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ExtendedIngredient(
    @Json(name = "amount")
    val amount: Double? = null,
    @Json(name = "consistency")
    val consistency: String? = null,
    @Json(name = "image")
    val image: String? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "original")
    val original: String? = null,
    @Json(name = "unit")
    val unit: String? = null
) : Parcelable
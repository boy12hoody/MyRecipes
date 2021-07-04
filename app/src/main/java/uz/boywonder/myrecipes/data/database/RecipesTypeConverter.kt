package uz.boywonder.myrecipes.data.database

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import uz.boywonder.myrecipes.models.Recipes

class RecipesTypeConverter {

    val moshi = Moshi.Builder().build()
    val moshiAdapter = moshi.adapter(Recipes::class.java)

    @TypeConverter
    fun recipeToString(recipes: Recipes) : String {
        return moshiAdapter.toJson(recipes)
    }

    @TypeConverter
    fun stringToRecipe(string: String) : Recipes? {
        return moshiAdapter.fromJson(string)
    }

}
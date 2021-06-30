package uz.boywonder.myrecipes.data.database

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import uz.boywonder.myrecipes.models.Recipe

class RecipesTypeConverter {

    val moshi = Moshi.Builder().build()
    val type = Types.newParameterizedType(List::class.java, Recipe::class.java)
    val moshiAdapter = moshi.adapter<Recipe>(type)

    @TypeConverter
    fun recipeToString(recipe: Recipe) : String {
        return moshiAdapter.toJson(recipe)
    }

    @TypeConverter
    fun stringToRecipe(string: String) : Recipe? {
        return moshiAdapter.fromJson(string)
    }

}
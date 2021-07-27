package uz.boywonder.myrecipes.data.database

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import uz.boywonder.myrecipes.models.Recipes
import uz.boywonder.myrecipes.models.Result

class RecipesTypeConverter {

    private val moshi: Moshi = Moshi.Builder().build()
    private val moshiAdapterForRecipes: JsonAdapter<Recipes> = moshi.adapter(Recipes::class.java)
    private val moshiAdapterForResult: JsonAdapter<Result> = moshi.adapter(Result::class.java)

    @TypeConverter
    fun recipeToString(recipes: Recipes) : String {
        return moshiAdapterForRecipes.toJson(recipes)
    }

    @TypeConverter
    fun stringToRecipe(string: String) : Recipes? {
        return moshiAdapterForRecipes.fromJson(string)
    }

    @TypeConverter
    fun resultToString(result: Result) : String {
        return moshiAdapterForResult.toJson(result)
    }

    @TypeConverter
    fun stringToResult(string: String) : Result? {
        return moshiAdapterForResult.fromJson(string)
    }
}
package uz.boywonder.myrecipes.data

import retrofit2.Response
import uz.boywonder.myrecipes.data.network.RecipesApi
import uz.boywonder.myrecipes.models.Recipe
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val recipesApi: RecipesApi
){
    suspend fun getRecipes(queries: Map<String, String>) : Response<Recipe>{
        return recipesApi.getRepices(queries)
    }
}
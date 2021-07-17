package uz.boywonder.myrecipes.data

import retrofit2.Response
import uz.boywonder.myrecipes.data.network.RecipesApi
import uz.boywonder.myrecipes.models.Recipes
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val recipesApi: RecipesApi
){
    suspend fun getRecipes(queries: Map<String, String>) : Response<Recipes>{
        return recipesApi.getRecipes(queries)
    }

    suspend fun searchRecipes(searchQuery: Map<String, String>) : Response<Recipes> {
        return recipesApi.searchRecipes(searchQuery)
    }
}
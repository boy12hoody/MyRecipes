package uz.boywonder.myrecipes.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap
import uz.boywonder.myrecipes.models.Recipes

interface RecipesApi {

    @GET("/recipes/complexSearch")
    suspend fun getRepices(
        @QueryMap queries: Map<String, String>
    ) : Response<Recipes>
}
package uz.boywonder.myrecipes.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import uz.boywonder.myrecipes.data.Repository
import uz.boywonder.myrecipes.data.database.RecipesEntity
import uz.boywonder.myrecipes.models.Recipes
import uz.boywonder.myrecipes.util.NetworkResult
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /* ROOM DATABASE */
    // read local database for recipes list
    var readRecipes: LiveData<List<RecipesEntity>> = repository.local.readDatabase().asLiveData()

    private fun insertRecipes(recipesEntity: RecipesEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.insertRecipes(recipesEntity)
    }

    /* RETROFIT */
    // response data in the form of LiveData
    var recipesResponse: MutableLiveData<NetworkResult<Recipes>> = MutableLiveData()

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    // get results with internet connection checks covered
    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {

        // as soon as getRecipes fun is called, the response will be in Loading state.
        recipesResponse.value = NetworkResult.Loading()

        // 1-step is checking connection
        if (hasInternetConnection()) {
            try {
                // 2-step is getting response and handling it to result values check through handleRecipeResult()
                val response = repository.remote.getRecipes(queries)
                recipesResponse.value = handleRecipeResponse(response)

                // 3-step is caching the response to local database
                val recipes = recipesResponse.value!!.data
                if (recipes != null) {
                    offlineCacheRecipes(recipes)
                }

            } catch (e: Exception) {
                recipesResponse.value = NetworkResult.Error("Recipes not found.")
                Log.e("RecipesViewModel", e.message.toString())
            }

        } else {
            recipesResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun offlineCacheRecipes(recipes: Recipes) {
        val recipesEntity = RecipesEntity(recipes)
        insertRecipes(recipesEntity)
    }

    // 3-step is handling all response values and return the result back to recipeResponse.value
    private fun handleRecipeResponse(response: Response<Recipes>): NetworkResult<Recipes> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout.")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("No Recipe Found.")
            }
            response.isSuccessful -> {
                val recipeData = response.body()
                return NetworkResult.Success(recipeData!!)
            }
            else -> return NetworkResult.Error(response.message())
        }
    }


    // checking internet connection. returns true or false.
    private fun hasInternetConnection(): Boolean {
        val connectivityManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }


}
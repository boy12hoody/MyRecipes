package uz.boywonder.myrecipes.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.boywonder.myrecipes.data.DataStoreRepository
import uz.boywonder.myrecipes.util.Constants.Companion.API_KEY
import uz.boywonder.myrecipes.util.Constants.Companion.DEFAULT_DIET_TYPE
import uz.boywonder.myrecipes.util.Constants.Companion.DEFAULT_MEAL_TYPE
import uz.boywonder.myrecipes.util.Constants.Companion.DEFAULT_RECIPES_COUNT
import uz.boywonder.myrecipes.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import uz.boywonder.myrecipes.util.Constants.Companion.QUERY_API_KEY
import uz.boywonder.myrecipes.util.Constants.Companion.QUERY_DIET
import uz.boywonder.myrecipes.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import uz.boywonder.myrecipes.util.Constants.Companion.QUERY_MEAL
import uz.boywonder.myrecipes.util.Constants.Companion.QUERY_NUMBER
import uz.boywonder.myrecipes.util.Constants.Companion.QUERY_SEARCH
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietTypes(mealType, mealTypeId, dietType, dietTypeId)
        }


    fun applyQueries(): HashMap<String, String> {
        val queries = HashMap<String, String>()

        viewModelScope.launch {
            readMealAndDietType.collect { values ->
                mealType = values.selectedMealType
                dietType = values.selectedDietType
            }
        }

        queries[QUERY_NUMBER] = DEFAULT_RECIPES_COUNT
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_MEAL] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

    fun searchQueries(searchQuery: String) : HashMap<String, String> {
        val queries : HashMap<String, String> = HashMap()
        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_COUNT
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

    // To notify user when Internet is connected again
    // Needed to avoid the scenario when user is notified every time the fragment is created
    var isOffline = false

}
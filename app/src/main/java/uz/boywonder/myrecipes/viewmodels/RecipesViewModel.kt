package uz.boywonder.myrecipes.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.boywonder.myrecipes.util.Constants.Companion.API_KEY
import uz.boywonder.myrecipes.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import uz.boywonder.myrecipes.util.Constants.Companion.QUERY_API_KEY
import uz.boywonder.myrecipes.util.Constants.Companion.QUERY_DIET
import uz.boywonder.myrecipes.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import uz.boywonder.myrecipes.util.Constants.Companion.QUERY_NUMBER
import uz.boywonder.myrecipes.util.Constants.Companion.QUERY_TYPE
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

     fun applyQueries(): HashMap<String, String> {
        val queries = HashMap<String, String>()

        queries[QUERY_NUMBER] = "50"
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = "breakfast"
        queries[QUERY_DIET] = "vegan"
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

}
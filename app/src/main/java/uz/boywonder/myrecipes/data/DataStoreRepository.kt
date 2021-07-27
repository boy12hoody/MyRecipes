package uz.boywonder.myrecipes.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import uz.boywonder.myrecipes.util.Constants.Companion.DEFAULT_DIET_TYPE
import uz.boywonder.myrecipes.util.Constants.Companion.DEFAULT_MEAL_TYPE
import uz.boywonder.myrecipes.util.Constants.Companion.PREFERENCES_DIET_TYPE
import uz.boywonder.myrecipes.util.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import uz.boywonder.myrecipes.util.Constants.Companion.PREFERENCES_MEAL_TYPE
import uz.boywonder.myrecipes.util.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import uz.boywonder.myrecipes.util.Constants.Companion.PREFERENCES_NAME
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context){

    private val dataStore = context.dataStore

    private object PreferencesKeys{
        val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)
    }

    suspend fun saveMealAndDietTypes(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.selectedMealType] = mealType
            preferences[PreferencesKeys.selectedMealTypeId] = mealTypeId
            preferences[PreferencesKeys.selectedDietType] = dietType
            preferences[PreferencesKeys.selectedDietTypeId] = dietTypeId
        }
    }

    val readMealAndDietType : Flow<MealAndDietType> = dataStore.data
        .catch { exception->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            MealAndDietType(
                preferences[PreferencesKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE,
                preferences[PreferencesKeys.selectedMealTypeId] ?: 0,
                preferences[PreferencesKeys.selectedDietType] ?: DEFAULT_DIET_TYPE,
                preferences[PreferencesKeys.selectedDietTypeId] ?: 0
            )
        }

}

data class MealAndDietType (
    val selectedMealType : String,
    val selectedMealTypeId : Int,
    val selectedDietType : String,
    val selectedDietTypeId : Int
)
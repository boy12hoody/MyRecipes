package uz.boywonder.myrecipes.data

import kotlinx.coroutines.flow.Flow
import uz.boywonder.myrecipes.data.database.RecipesDao
import uz.boywonder.myrecipes.data.database.RecipesEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

     fun readDatabase() : Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }
}
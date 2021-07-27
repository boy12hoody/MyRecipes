package uz.boywonder.myrecipes.data

import kotlinx.coroutines.flow.Flow
import uz.boywonder.myrecipes.data.database.RecipesDao
import uz.boywonder.myrecipes.data.database.entities.FavoriteEntity
import uz.boywonder.myrecipes.data.database.entities.RecipesEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

     fun readRecipes() : Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    fun readFavoriteRecipe() : Flow<List<FavoriteEntity>> {
        return recipesDao.readFavoriteRecipe()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    suspend fun insertFavoriteRecipe(favoriteEntity: FavoriteEntity) {
        recipesDao.insertFavoriteRecipe(favoriteEntity)
    }

    suspend fun deleteFavoriteRecipe(favoriteEntity: FavoriteEntity) {
        recipesDao.deleteFavoriteRecipe(favoriteEntity)
    }

    suspend fun deleteAllFavoriteRecipes() {
        recipesDao.deleteAllFavoriteRecipes()
    }
}
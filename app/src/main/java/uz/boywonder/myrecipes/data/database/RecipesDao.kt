package uz.boywonder.myrecipes.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uz.boywonder.myrecipes.data.database.entities.FavoriteEntity
import uz.boywonder.myrecipes.data.database.entities.RecipesEntity

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipe(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readRecipes() : Flow<List<RecipesEntity>>

    @Query("SELECT * FROM favorite_recipes_table ORDER BY id ASC")
    fun readFavoriteRecipe() : Flow<List<FavoriteEntity>>

    @Delete
    suspend fun deleteFavoriteRecipe(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM favorite_recipes_table")
    suspend fun deleteAllFavoriteRecipes()
}
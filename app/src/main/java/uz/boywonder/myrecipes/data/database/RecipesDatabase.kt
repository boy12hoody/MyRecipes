package uz.boywonder.myrecipes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.boywonder.myrecipes.data.database.entities.FavoriteEntity
import uz.boywonder.myrecipes.data.database.entities.RecipesEntity

@Database(
    entities = [RecipesEntity::class, FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase : RoomDatabase() {

    abstract fun recipesDao() : RecipesDao

}
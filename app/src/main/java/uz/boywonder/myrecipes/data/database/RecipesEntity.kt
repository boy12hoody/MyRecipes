package uz.boywonder.myrecipes.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.boywonder.myrecipes.models.Recipe
import uz.boywonder.myrecipes.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var recipe: Recipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}
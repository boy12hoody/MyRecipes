package uz.boywonder.myrecipes.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.boywonder.myrecipes.models.Recipes
import uz.boywonder.myrecipes.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var recipes: Recipes
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}
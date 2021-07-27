package uz.boywonder.myrecipes.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.boywonder.myrecipes.models.Result
import uz.boywonder.myrecipes.util.Constants.Companion.FAVORITE_RECIPES_TABLE

@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)
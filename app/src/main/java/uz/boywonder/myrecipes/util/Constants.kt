package uz.boywonder.myrecipes.util

class Constants {

    companion object {

        const val API_KEY = "151da19c12ea40b1928150fde5e0705f"
        const val BASE_URL = "https://api.spoonacular.com"

        // API QUERIES
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"

        // ROOM
        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"
    }
}
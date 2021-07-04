package uz.boywonder.myrecipes.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.boywonder.myrecipes.data.database.RecipesDatabase
import uz.boywonder.myrecipes.util.Constants.Companion.DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, RecipesDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDatabaseDao(recipesDatabase: RecipesDatabase) = recipesDatabase.recipesDao()
}
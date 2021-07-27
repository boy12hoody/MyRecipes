package uz.boywonder.myrecipes.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.viewbinding.library.activity.viewBinding
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import uz.boywonder.myrecipes.R
import uz.boywonder.myrecipes.adapters.PagerAdapter
import uz.boywonder.myrecipes.data.database.entities.FavoriteEntity
import uz.boywonder.myrecipes.databinding.ActivityDetailsBinding
import uz.boywonder.myrecipes.ui.fragments.ingredients.IngredientsFragment
import uz.boywonder.myrecipes.ui.fragments.instructions.InstructionsFragment
import uz.boywonder.myrecipes.ui.fragments.overview.OverviewFragment
import uz.boywonder.myrecipes.util.Constants.Companion.RECIPE_RESULT_KEY
import uz.boywonder.myrecipes.viewmodels.MainViewModel

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val binding: ActivityDetailsBinding by viewBinding()
    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()

    private var recipeId = 0
    private var isRecipeSaved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPE_RESULT_KEY, args.result)

        val pagerAdapter = PagerAdapter(resultBundle, fragments, this)

        binding.apply {

            viewPager2.isUserInputEnabled = false
            viewPager2.adapter = pagerAdapter

            TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                tab.text = titles[position]
            }.attach()

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_action_menu, menu)
        val addToFavMenuItem = menu!!.findItem(R.id.action_save_to_favorites)
        checkFavSavedRecipe(addToFavMenuItem)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.action_save_to_favorites -> {
                if (!isRecipeSaved) {
                    saveToFavorites(item)
                } else {
                    removeFromFavorites(item)
                }

            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkFavSavedRecipe(addToFavMenuItem: MenuItem) {
        mainViewModel.readFavoriteRecipes.observe(this) { favoriteEntities ->
            try {
                for (entity in favoriteEntities) {
                    if (entity.result.id == args.result.id) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            changeMenuItemColor(addToFavMenuItem, R.color.yellow)
                        }
                        recipeId = entity.id
                        isRecipeSaved = true
                    }
                }
            } catch (e: Exception) {
                Log.e("DetailsActivity: checkFavSavedRecipe()", e.message.toString())
            }
        }
    }

    private fun saveToFavorites(menuItem: MenuItem) {
        val favoriteEntity = FavoriteEntity(0, args.result)
        mainViewModel.insertFavoriteRecipe(favoriteEntity)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            changeMenuItemColor(menuItem, R.color.yellow)
        }
        showSnackBar("Recipe saved.", "Okay")
        isRecipeSaved = true
    }

    private fun removeFromFavorites(menuItem: MenuItem) {
        val favoriteEntity = FavoriteEntity(recipeId, args.result)
        mainViewModel.deleteFavoriteRecipe(favoriteEntity)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            changeMenuItemColor(menuItem, R.color.white)
        }
        showSnackBar("Recipe removed.", "Okay")
        isRecipeSaved = false
    }



    private fun showSnackBar(message: String, actionMessage: String) {
        Snackbar.make(binding.detailsLayout, message, Snackbar.LENGTH_SHORT)
            .setAction(actionMessage) {}.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.iconTintList = ContextCompat.getColorStateList(this, color)
    }
}
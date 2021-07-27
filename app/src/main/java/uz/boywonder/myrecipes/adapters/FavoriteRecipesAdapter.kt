package uz.boywonder.myrecipes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.jsoup.Jsoup
import uz.boywonder.myrecipes.R
import uz.boywonder.myrecipes.data.database.entities.FavoriteEntity
import uz.boywonder.myrecipes.databinding.RecipesRowLayoutBinding
import uz.boywonder.myrecipes.util.MyDiffUtil

class FavoriteRecipesAdapter() : RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>() {

    private var favoriteRecipes = emptyList<FavoriteEntity>()

    inner class MyViewHolder(private val binding: RecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoriteEntity: FavoriteEntity) {
            binding.apply {

                titleTextView.text = favoriteEntity.result.title
                descriptionTextView.apply {
                    if (favoriteEntity.result.summary != null) {
                        val desc = Jsoup.parse(favoriteEntity.result.summary!!).text()
                        text = desc.toString()
                    }
                }

                heartTextView.text = favoriteEntity.result.aggregateLikes.toString()
                clockTextView.text = favoriteEntity.result.readyInMinutes.toString()
                recipeImageView.load(favoriteEntity.result.image) {
                    crossfade(600)
                    error(R.drawable.ic_error_image)
                }

                if (favoriteEntity.result.vegan) {
                    leafImageView.setColorFilter(
                        ContextCompat.getColor(
                            leafImageView.context,
                            R.color.green
                        )
                    )
                    leafTextView.setTextColor(
                        ContextCompat.getColor(
                            leafTextView.context,
                            R.color.green
                        )
                    )
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            RecipesRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentFavoriteRecipe = favoriteRecipes[position]
        holder.bind(currentFavoriteRecipe)
    }

    override fun getItemCount(): Int {
        return favoriteRecipes.size
    }

    fun setNewData(newData: List<FavoriteEntity>) {
        val diffUtil = MyDiffUtil(favoriteRecipes, newData)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        favoriteRecipes = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}
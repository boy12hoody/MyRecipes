package uz.boywonder.myrecipes.adapters

import android.graphics.ColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import uz.boywonder.myrecipes.R
import uz.boywonder.myrecipes.databinding.RecipesRowLayoutBinding
import uz.boywonder.myrecipes.models.Recipe
import uz.boywonder.myrecipes.models.Result
import uz.boywonder.myrecipes.util.RecipesDiffUtil

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    private var recipes = emptyList<Result>()

    class MyViewHolder(private val binding: RecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {

            // here we bind Result elements values to UI elements values
            binding.apply {

                titleTextView.text = result.title
                descriptionTextView.text = result.summary
                heartTextView.text = result.aggregateLikes.toString()
                clockTextView.text = result.readyInMinutes.toString()
                recipeImageView.load(result.image) {
                    crossfade(600)
                }

                if (result.vegan) {
                    leafImageView.setColorFilter(ContextCompat.getColor(leafImageView.context, R.color.green))
                    leafTextView.setTextColor(ContextCompat.getColor(leafTextView.context, R.color.green))
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
        val currentResult = recipes[position]
        holder.bind(currentResult)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    // To check the updated data with older one to improve performance and accuracy of the app
    fun setNewData(newData: Recipe) {
        val diffUtil = RecipesDiffUtil(recipes, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        recipes = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }

}
package uz.boywonder.myrecipes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import uz.boywonder.myrecipes.databinding.RecipesRowLayoutBinding
import uz.boywonder.myrecipes.models.Recipe
import uz.boywonder.myrecipes.models.Result
import uz.boywonder.myrecipes.util.RecipesDiffUtil

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    private var recipe = emptyList<Result>()

    class MyViewHolder(private val binding: RecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {

            // here we bind Result elements values to UI elements values
            TODO(
                "I use viewBinding instead of DataBinding so" +
                        " we gotta manually bind the values to each ui element here"
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            RecipesRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentResult = recipe[position]
        holder.bind(currentResult)
    }

    override fun getItemCount(): Int {
        return recipe.size
    }

    // To check the updated data with older one to improve performance and accuracy of the app
    fun setNewData(newData: Recipe) {
        val diffUtil = RecipesDiffUtil(recipe, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        recipe = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }

}
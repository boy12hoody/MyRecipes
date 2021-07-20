package uz.boywonder.myrecipes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import uz.boywonder.myrecipes.R
import uz.boywonder.myrecipes.databinding.RecipesRowLayoutBinding
import uz.boywonder.myrecipes.models.Recipes
import uz.boywonder.myrecipes.models.Result
import uz.boywonder.myrecipes.util.RecipesDiffUtil

class RecipesAdapter(
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    private var recipes = emptyList<Result>()

    inner class MyViewHolder(private val binding: RecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = recipes[position]
                    listener.onItemClick(item)
                }
            }
        }

        fun bind(result: Result) {

            // here we bind Result elements values to UI elements values
            binding.apply {

                titleTextView.text = result.title
                descriptionTextView.text = result.summary
                heartTextView.text = result.aggregateLikes.toString()
                clockTextView.text = result.readyInMinutes.toString()
                recipeImageView.load(result.image) {
                    crossfade(600)
                    error(R.drawable.ic_error_image)
                }

                if (result.vegan) {
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

    interface OnItemClickListener {
        fun onItemClick(result: Result)
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
    fun setNewData(newData: Recipes) {
        val diffUtil = RecipesDiffUtil(recipes, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        recipes = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }

}
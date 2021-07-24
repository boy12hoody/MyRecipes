package uz.boywonder.myrecipes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import uz.boywonder.myrecipes.R
import uz.boywonder.myrecipes.databinding.IngredientsRowLayoutBinding
import uz.boywonder.myrecipes.models.ExtendedIngredient
import uz.boywonder.myrecipes.util.Constants.Companion.BASE_IMAGE_URL
import uz.boywonder.myrecipes.util.RecipesDiffUtil

class IngredientsAdapter() : RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    inner class MyViewHolder(private val binding: IngredientsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(extendedIngredient: ExtendedIngredient) {
            binding.apply {
                ingredientImageView.load(BASE_IMAGE_URL + extendedIngredient.image) {
                    crossfade(600)
                    placeholder(R.drawable.ic_error_image)
                }
                ingredientNameTextView.text =
                    extendedIngredient.name?.replaceFirstChar { it.uppercase() }
                ingredientAmount.text = extendedIngredient.amount.toString()
                ingredientUnit.text = extendedIngredient.unit
                ingredientConsistency.text = extendedIngredient.consistency
                ingredientOriginal.text = extendedIngredient.original
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            IngredientsRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentIngredient = ingredientsList[position]
        holder.bind(currentIngredient)

    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    fun setNewData(newIngredientsList: List<ExtendedIngredient>) {
        val diffUtil = RecipesDiffUtil(ingredientsList, newIngredientsList)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        ingredientsList = newIngredientsList
        diffUtilResult.dispatchUpdatesTo(this)
    }
}
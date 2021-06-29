package uz.boywonder.myrecipes.ui.fragments.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import uz.boywonder.myrecipes.R
import uz.boywonder.myrecipes.RecipesViewModel
import uz.boywonder.myrecipes.adapters.RecipesAdapter
import uz.boywonder.myrecipes.databinding.FragmentRecipesBinding
import uz.boywonder.myrecipes.util.Constants.Companion.API_KEY
import uz.boywonder.myrecipes.util.NetworkResult

@AndroidEntryPoint
class RecipesFragment : Fragment(R.layout.fragment_recipes) {

    private val viewModel: RecipesViewModel by viewModels()
    private val binding: FragmentRecipesBinding by viewBinding()
    private val recipesAdapter by lazy { RecipesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        requestApiData()

    }

    private fun requestApiData() {
        viewModel.getRecipes(applyQueries())
        viewModel.recipeResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { recipesAdapter.setNewData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(context, response.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun applyQueries(): HashMap<String, String> {
        val queries = HashMap<String, String>()

        queries["number"] = "50"
        queries["apiKey"] = API_KEY
        queries["type"] = "breakfast"
        queries["diet"] = "vegan"
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"

        return queries
    }

    private fun showShimmerEffect() {
        binding.shimmerFrameLayout.startShimmer()
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.recyclerview.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.recyclerview.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        binding.recyclerview.adapter = recipesAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

}
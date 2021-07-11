package uz.boywonder.myrecipes.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import uz.boywonder.myrecipes.R
import uz.boywonder.myrecipes.adapters.RecipesAdapter
import uz.boywonder.myrecipes.data.database.RecipesEntity
import uz.boywonder.myrecipes.databinding.FragmentRecipesBinding
import uz.boywonder.myrecipes.models.Recipes
import uz.boywonder.myrecipes.util.NetworkResult
import uz.boywonder.myrecipes.util.observeOnce
import uz.boywonder.myrecipes.viewmodels.MainViewModel
import uz.boywonder.myrecipes.viewmodels.RecipesViewModel

@AndroidEntryPoint
class RecipesFragment : Fragment(R.layout.fragment_recipes) {

    private val mainViewModel: MainViewModel by viewModels()
    private val recipesViewModel: RecipesViewModel by viewModels()
    private val binding: FragmentRecipesBinding by viewBinding()
    private val recipesAdapter by lazy { RecipesAdapter() }
    private val args by navArgs<RecipesFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        readDatabase()

        binding.recipesFab.setOnClickListener {
            findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
        }

    }

    /* Because the query is hardcoded for now, it is ok to show the last cached data.
    TODO subject to change once query search is introduced */
    private fun readDatabase() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
                    Log.d("RecipesFragment", "readDatabase() Called")
                    recipesAdapter.setNewData(database.first().recipes)
                    hideShimmerEffect()
                } else {
                    requestApiData()
                }
            }
        }
    }

    // Requesting Data from Api, handles shimmer effect state as well
    private fun requestApiData() {
        Log.d("RecipesFragment", "requestApiData() Called")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { recipesAdapter.setNewData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache(response)
                    Toast.makeText(context, response.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    // Loading the Cached data in case apiResponse throws an error, shows the last cached database
    // asks for apiResponse to meet handleErrorMessage(apiResponse, database) parameters.
    private fun loadDataFromCache(apiResponse: NetworkResult<Recipes>) {
        Log.d("RecipesFragment", "loadDataFromCache() Called")
        lifecycleScope.launchWhenStarted {
            mainViewModel.readRecipes.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    recipesAdapter.setNewData(database.first().recipes)
                } else {
                    handleErrorMessage(apiResponse, database)
                }
            }
        }
    }

    // Handling the error message whenever ApiResponse/Database is Error/NullOrEmpty
    private fun handleErrorMessage(apiResponse: NetworkResult<Recipes>?, database: List<RecipesEntity>?) {
        Log.d("RecipesFragment", "handleErrorMessage() Called")
        if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {
            binding.apply {
                errorImageView.visibility = View.VISIBLE
                errorTextView.visibility = View.VISIBLE
                errorTextView.text = apiResponse.message.toString()
            }
        } else if (apiResponse is NetworkResult.Loading) {
            binding.apply {
                errorTextView.visibility = View.INVISIBLE
                errorImageView.visibility = View.INVISIBLE
            }
        } else if (apiResponse is NetworkResult.Success) {
            binding.apply {
                errorTextView.visibility = View.INVISIBLE
                errorImageView.visibility = View.INVISIBLE
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerview.adapter = recipesAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
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

}
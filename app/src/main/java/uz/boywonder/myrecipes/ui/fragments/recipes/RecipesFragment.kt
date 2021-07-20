package uz.boywonder.myrecipes.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.boywonder.myrecipes.R
import uz.boywonder.myrecipes.adapters.RecipesAdapter
import uz.boywonder.myrecipes.data.database.RecipesEntity
import uz.boywonder.myrecipes.databinding.FragmentRecipesBinding
import uz.boywonder.myrecipes.models.Recipes
import uz.boywonder.myrecipes.models.Result
import uz.boywonder.myrecipes.util.NetworkListener
import uz.boywonder.myrecipes.util.NetworkResult
import uz.boywonder.myrecipes.util.observeOnce
import uz.boywonder.myrecipes.viewmodels.MainViewModel
import uz.boywonder.myrecipes.viewmodels.RecipesViewModel

@AndroidEntryPoint
class RecipesFragment : Fragment(R.layout.fragment_recipes),
    androidx.appcompat.widget.SearchView.OnQueryTextListener, RecipesAdapter.OnItemClickListener {

    private val mainViewModel: MainViewModel by viewModels()
    private val recipesViewModel: RecipesViewModel by viewModels()
    private val binding: FragmentRecipesBinding by viewBinding()
    private val recipesAdapter by lazy { RecipesAdapter(this) }
    private val args by navArgs<RecipesFragmentArgs>()

    private lateinit var networkListener: NetworkListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        // Setup Action Menu
        setHasOptionsMenu(true)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                networkListener = NetworkListener()
                networkListener.checkNetworkAvailability(requireContext()).collect { status ->
                    Log.d("RecipesFragment: NetworkListener", status.toString())

                    when (status) {

                        true -> {
                            if (recipesViewModel.isOffline) {
                                Snackbar.make(
                                    requireView(), "Back Online.", Snackbar.LENGTH_SHORT
                                ).show()

                                recipesViewModel.isOffline = false
                            }
                        }

                        false -> {
                            Snackbar.make(
                                requireView(), "No Internet Connection.", Snackbar.LENGTH_SHORT
                            ).show()

                            recipesViewModel.isOffline = true
                        }

                    }
                    // Every time network status changes, reads local database first
                    readDatabase()
                }
            }
        }

        binding.recipesFab.setOnClickListener {
            findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
        }

    }

    /* Because the query is hardcoded for now, it is ok to show the last cached data.
    TODO subject to change once query search is introduced */
    private fun readDatabase() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
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

    private fun searchApiData(searchQuery: String) {
        Log.d("RecipesFragment", "searchApiData() Called")
        showShimmerEffect()
        mainViewModel.searchRecipes(recipesViewModel.searchQueries(searchQuery))
        mainViewModel.searchRecipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    val recipes = response.data
                    recipes?.let { recipesAdapter.setNewData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache(response)
                    Snackbar.make(requireView(), response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
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
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.readRecipes.observe(viewLifecycleOwner) { database ->
                    if (database.isNotEmpty()) {
                        recipesAdapter.setNewData(database.first().recipes)
                    } else {
                        handleErrorMessage(apiResponse, database)
                    }
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

    // Action Menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_action_menu, menu)
        val actionSearch = menu.findItem(R.id.action_search)
        val searchView = actionSearch.actionView as? androidx.appcompat.widget.SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    // Action Menu SearchView Submit
    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrBlank()) {
            searchApiData(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    // Handle the click on Recipe Item
    override fun onItemClick(result: Result) {
        val action = RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(result)
        findNavController().navigate(action)
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
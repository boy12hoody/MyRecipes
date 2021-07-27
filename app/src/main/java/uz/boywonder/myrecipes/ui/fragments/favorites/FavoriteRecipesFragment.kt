package uz.boywonder.myrecipes.ui.fragments.favorites

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import uz.boywonder.myrecipes.R
import uz.boywonder.myrecipes.adapters.FavoriteRecipesAdapter
import uz.boywonder.myrecipes.databinding.FragmentFavoriteRecipesBinding
import uz.boywonder.myrecipes.viewmodels.MainViewModel

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment(R.layout.fragment_favorite_recipes) {

    private val binding: FragmentFavoriteRecipesBinding by viewBinding()
    private val mainViewModel: MainViewModel by viewModels()
    private val favoriteRecipesAdapter by lazy { FavoriteRecipesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        mainViewModel.readFavoriteRecipes.observe(viewLifecycleOwner) { favoriteEntities ->
            if (favoriteEntities.isNotEmpty()) {
                binding.apply {
                    noFavTextView.visibility = View.INVISIBLE
                    noFavImageView.visibility = View.INVISIBLE
                    favoriteRecipesRecyclerView.visibility = View.VISIBLE
                }
                favoriteRecipesAdapter.setNewData(favoriteEntities)

            } else {
                binding.apply {
                    noFavTextView.visibility = View.VISIBLE
                    noFavImageView.visibility = View.VISIBLE
                    favoriteRecipesRecyclerView.visibility = View.INVISIBLE
                }
            }
        }

    }

    private fun setupRecyclerView() {
        binding.favoriteRecipesRecyclerView.apply {
            adapter = favoriteRecipesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}
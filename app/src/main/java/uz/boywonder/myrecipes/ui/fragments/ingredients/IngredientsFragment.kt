package uz.boywonder.myrecipes.ui.fragments.ingredients

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import uz.boywonder.myrecipes.R
import uz.boywonder.myrecipes.adapters.IngredientsAdapter
import uz.boywonder.myrecipes.databinding.FragmentIngredientsBinding
import uz.boywonder.myrecipes.models.Result
import uz.boywonder.myrecipes.util.Constants.Companion.RECIPE_RESULT_KEY

class IngredientsFragment : Fragment(R.layout.fragment_ingredients) {

    private val binding: FragmentIngredientsBinding by viewBinding()
    private val ingredientsAdapter by lazy { IngredientsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val myBundle: Result = args!!.getParcelable<Result>(RECIPE_RESULT_KEY) as Result

        setupRecyclerView()

        myBundle.extendedIngredients?.let { ingredientsAdapter.setNewData(it) }
    }

    private fun setupRecyclerView() {
        binding.ingredientsRecyclerView.apply {
            adapter = ingredientsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}
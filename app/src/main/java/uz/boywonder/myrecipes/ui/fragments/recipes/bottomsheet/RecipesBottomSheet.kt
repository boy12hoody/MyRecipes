package uz.boywonder.myrecipes.ui.fragments.recipes.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.bottomsheetdialogfragment.viewBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import uz.boywonder.myrecipes.databinding.RecipesBottomSheetBinding
import uz.boywonder.myrecipes.util.Constants.Companion.DEFAULT_DIET_TYPE
import uz.boywonder.myrecipes.util.Constants.Companion.DEFAULT_MEAL_TYPE
import uz.boywonder.myrecipes.viewmodels.RecipesViewModel


class RecipesBottomSheet : BottomSheetDialogFragment() {

    private val binding: RecipesBottomSheetBinding by viewBinding()
    private lateinit var recipesViewModel: RecipesViewModel

    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // read cached values of chips from DataStore and update UI choices
        recipesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner, { value ->
            mealTypeChip = value.selectedMealType
            dietTypeChip = value.selectedDietType
            updateChip(value.selectedMealTypeId, binding.mealTypeChipGroup)
            updateChip(value.selectedDietTypeId, binding.dietTypeChipGroup)
        })

        binding.apply {
            mealTypeChipGroup.setOnCheckedChangeListener { group, checkedChipId ->
                val currentChip = group.findViewById<Chip>(checkedChipId)
                val selectedMealType = currentChip.text.toString().lowercase()
                mealTypeChip = selectedMealType
                mealTypeChipId = checkedChipId
            }
            dietTypeChipGroup.setOnCheckedChangeListener { group, checkedChipId ->
                val currentChip = group.findViewById<Chip>(checkedChipId)
                val selectedDietType = currentChip.text.toString().lowercase()
                dietTypeChip = selectedDietType
                dietTypeChipId = checkedChipId
            }

            applyBtn.setOnClickListener {
                recipesViewModel.saveMealAndDietType(
                    mealTypeChip,
                    mealTypeChipId,
                    dietTypeChip,
                    dietTypeChipId
                )

                // go back to Recipes Fragment with Argument
                val action =
                    RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipesFragment(true)
                findNavController().navigate(action)
            }
        }
    }

    private fun updateChip(selectedChipId: Int, selectedChipGroup: ChipGroup) {
        if (selectedChipId != 0) {
            try {
                selectedChipGroup.findViewById<Chip>(selectedChipId).isChecked = true
            } catch (e: Exception) {
                Log.e("RecipesBottomSheet", e.message.toString())
            }
        }
    }
}
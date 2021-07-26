package uz.boywonder.myrecipes.ui.fragments.instructions

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import uz.boywonder.myrecipes.R
import uz.boywonder.myrecipes.databinding.FragmentInstructionsBinding
import uz.boywonder.myrecipes.models.Result
import uz.boywonder.myrecipes.util.Constants


class InstructionsFragment : Fragment(R.layout.fragment_instructions) {

    private val binding: FragmentInstructionsBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val myBundle: Result = args!!.getParcelable<Result>(Constants.RECIPE_RESULT_KEY) as Result

        binding.apply {
            ingredientsWebView.webViewClient = object : WebViewClient() {}

            if (myBundle.sourceUrl != null) {
                ingredientsWebView.loadUrl(myBundle.sourceUrl)
            } else {
                Snackbar.make(
                    requireView(), "No Website Found For Instructions.", Snackbar.LENGTH_SHORT
                ).show()
            }

        }

    }

}
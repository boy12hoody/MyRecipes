package uz.boywonder.myrecipes.ui.fragments.overview

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import coil.load
import org.jsoup.Jsoup
import uz.boywonder.myrecipes.R
import uz.boywonder.myrecipes.databinding.FragmentOverviewBinding
import uz.boywonder.myrecipes.models.Result
import uz.boywonder.myrecipes.util.Constants.Companion.RECIPE_RESULT_KEY

class OverviewFragment : Fragment(R.layout.fragment_overview) {

    private val binding: FragmentOverviewBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val myBundle: Result = args!!.getParcelable<Result>(RECIPE_RESULT_KEY) as Result

        binding.apply {
            mainImageView.load(myBundle.image)
            titleTextView.text = myBundle.title
            timeTextView.text = myBundle.readyInMinutes.toString()
            likesTextView.text = myBundle.aggregateLikes.toString()
            summaryTextView.apply {
                if (myBundle.summary != null) {
                    val summary = Jsoup.parse(myBundle.summary).text()
                    text = summary
                }
            }

            updateColors(myBundle.vegetarian, vegetarianTextView, vegetarianImageView)
            updateColors(myBundle.vegan, veganTextView, veganImageView)
            updateColors(myBundle.dairyFree, dairyFreeTextView, dairyFreeImageView)
            updateColors(myBundle.glutenFree, glutenFreeTextView, glutenFreeImageView)
            updateColors(myBundle.veryHealthy, healthyTextView, healthyImageView)
            updateColors(myBundle.cheap, cheapTextView, cheapImageView)
        }
    }

    private fun updateColors(stateIsTrue: Boolean, textView: TextView, imageView: ImageView) {
        if (stateIsTrue) {
            imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
    }

}
package uz.boywonder.myrecipes.ui

import android.os.Bundle
import android.view.MenuItem
import android.viewbinding.library.activity.viewBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import uz.boywonder.myrecipes.R
import uz.boywonder.myrecipes.adapters.PagerAdapter
import uz.boywonder.myrecipes.databinding.ActivityDetailsBinding
import uz.boywonder.myrecipes.ui.fragments.ingredients.IngredientsFragment
import uz.boywonder.myrecipes.ui.fragments.instructions.InstructionsFragment
import uz.boywonder.myrecipes.ui.fragments.overview.OverviewFragment

class DetailsActivity : AppCompatActivity() {

    private val binding: ActivityDetailsBinding by viewBinding()
    private val args by navArgs<DetailsActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable("recipeBundle", args.result)

        val pagerAdapter = PagerAdapter(resultBundle, fragments, this)

        binding.apply {

            viewPager2.isUserInputEnabled = false
            viewPager2.adapter = pagerAdapter

            TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                tab.text = titles[position]
            }.attach()

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
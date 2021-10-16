package ir.erfan_mh_at.test_ba_salam.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.erfan_mh_at.test_ba_salam.R
import ir.erfan_mh_at.test_ba_salam.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    var icBackOnClickListener: (() -> Unit)? = null
    var icMenuOnClickListener: (() -> Unit)? = null
    var icSearchOnClickListener: (() -> Unit)? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configure()
    }

    override fun onResume() {
        super.onResume()
        setupOnDestinationChangedListener()
    }

    private fun configure() {
        setSupportActionBar(binding.toolbar)
        setOnClicks()
    }

    private fun setOnClicks() {
        binding.icBack.setOnClickListener {
            icBackOnClickListener?.let { it() }
        }

        binding.icSearch.setOnClickListener {
            icSearchOnClickListener?.let { it() }
        }

        binding.icMenu.setOnClickListener {
            icMenuOnClickListener?.let { it() }
        }
    }

    private fun setupOnDestinationChangedListener() {
        findNavController(R.id.nav_host_fragment)
            .addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.animalAndFlowerSearchFragment -> {
                        binding.toolbar.visibility = View.GONE
                    }
                    else -> {
                        binding.toolbar.visibility = View.VISIBLE
                    }
                }
            }
    }
}
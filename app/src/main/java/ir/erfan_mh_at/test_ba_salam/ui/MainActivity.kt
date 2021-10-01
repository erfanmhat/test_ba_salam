package ir.erfan_mh_at.test_ba_salam.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import ir.erfan_mh_at.test_ba_salam.databinding.ActivityMainBinding
import ir.erfan_mh_at.test_ba_salam.ui.viewModels.AnimalAndFlowerViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var animalAndFlowerViewModel: AnimalAndFlowerViewModel

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

    private fun configure() {
        setSupportActionBar(binding.toolbar)
        setupViewModels()
        setOnClicks()
    }

    private fun setupViewModels() {
        animalAndFlowerViewModel = ViewModelProvider(this).get(AnimalAndFlowerViewModel::class.java)
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
}
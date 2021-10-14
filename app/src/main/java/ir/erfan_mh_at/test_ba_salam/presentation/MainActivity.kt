package ir.erfan_mh_at.test_ba_salam.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
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
}
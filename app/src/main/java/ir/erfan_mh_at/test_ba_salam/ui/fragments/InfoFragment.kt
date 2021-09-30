package ir.erfan_mh_at.test_ba_salam.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import ir.erfan_mh_at.test_ba_salam.R
import ir.erfan_mh_at.test_ba_salam.databinding.FragmentInfoBinding
import ir.erfan_mh_at.test_ba_salam.other.addComma
import ir.erfan_mh_at.test_ba_salam.other.commonLetters
import ir.erfan_mh_at.test_ba_salam.ui.adapters.AnimalAndFlowerAdapter
import ir.erfan_mh_at.test_ba_salam.ui.adapters.AnimalAndFlowerImageAdapter

class InfoFragment : Fragment() {

    private val args by navArgs<InfoFragmentArgs>()

    private lateinit var binding: FragmentInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configure()
    }

    private fun configure() {
        binding.apply {
            val animalName = args.animalAndFlower.animal.name
            val flowerName = args.animalAndFlower.flower.name
            tvAnimalAndFlowerName.text = getString(
                R.string.animal_and_flower_name,
                animalName,
                flowerName
            )
            val commonLetters = commonLetters(animalName, flowerName)
            tvNumberOfCommonLetters.text = commonLetters.length.toString()
            tvCommonLetters.text = getString(R.string.common_letters, commonLetters.addComma())

            vp2Images.adapter = AnimalAndFlowerImageAdapter(
                listOf(
                    args.animalAndFlower.animal.image,
                    args.animalAndFlower.flower.image
                )
            )

            TabLayoutMediator(tabLayout, vp2Images) { tab, position ->
                //Some implementation
            }.attach()
        }
    }
}
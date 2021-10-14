package ir.erfan_mh_at.test_ba_salam.presentation.animal_and_flower_info

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import ir.erfan_mh_at.test_ba_salam.R
import ir.erfan_mh_at.test_ba_salam.common.addComma
import ir.erfan_mh_at.test_ba_salam.common.commonLetters
import ir.erfan_mh_at.test_ba_salam.common.enNumberToFa
import ir.erfan_mh_at.test_ba_salam.databinding.FragmentAnimalAndFlowerInfoBinding
import ir.erfan_mh_at.test_ba_salam.presentation.MainActivity

@AndroidEntryPoint
class AnimalAndFlowerInfoFragment : Fragment() {

    private val animalAndFlowerInfoViewModel: AnimalAndFlowerInfoViewModel by viewModels()

    private val animalAndFlowerImageAdapter = AnimalAndFlowerImageAdapter()

    private lateinit var binding: FragmentAnimalAndFlowerInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimalAndFlowerInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configure()
    }

    private fun configure() {
        setupViewPager()
        readAnimalAndFlowerDataFromViewModel()
        setOnClicks()
    }

    private fun setupViewPager() {
        binding.vp2Images.adapter = animalAndFlowerImageAdapter
        TabLayoutMediator(binding.tabLayout, binding.vp2Images) { _, _ -> }.attach()
    }

    private fun readAnimalAndFlowerDataFromViewModel() {
        animalAndFlowerInfoViewModel.animalAndFlower.run {
            binding.tvAnimalAndFlowerName.text = getString(
                R.string.animal_and_flower_name, animal.name, flower.name
            )
            val commonLetters = commonLetters(animal.name, flower.name)
            binding.tvNumberOfCommonLetters.text = commonLetters.length.toString().enNumberToFa()
            binding.tvCommonLetters.text =
                getString(R.string.common_letters, commonLetters.addComma())

            animalAndFlowerImageAdapter.submitList(listOf(animal.image, flower.image))
        }
    }

    private fun setOnClicks() {
        val mainActivity = (activity as MainActivity)
        mainActivity.icMenuOnClickListener = {
            Toast.makeText(context, "info fragment menu!", Toast.LENGTH_LONG).show()
        }

        mainActivity.icSearchOnClickListener = {
            Toast.makeText(context, "info fragment search!", Toast.LENGTH_LONG).show()
        }

        mainActivity.icBackOnClickListener = {
            findNavController().navigate(R.id.action_animalAndFlowerInfoFragment_to_animalAndFlowerListFragment)
        }
    }
}
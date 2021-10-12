package ir.erfan_mh_at.test_ba_salam.presentation.animal_and_flower_info

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import ir.erfan_mh_at.test_ba_salam.R
import ir.erfan_mh_at.test_ba_salam.common.addComma
import ir.erfan_mh_at.test_ba_salam.common.commonLetters
import ir.erfan_mh_at.test_ba_salam.common.enNumberToFa
import ir.erfan_mh_at.test_ba_salam.databinding.FragmentAnimalAndFlowerInfoBinding
import ir.erfan_mh_at.test_ba_salam.presentation.MainActivity

class AnimalAndFlowerInfoFragment : Fragment() {

    private val args by navArgs<AnimalAndFlowerInfoFragmentArgs>()

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
        setupViews()
        setOnClicks()
    }

    private fun setupViews() {
        binding.apply {
            val animalName = args.animalAndFlower.animal.name
            val flowerName = args.animalAndFlower.flower.name
            tvAnimalAndFlowerName.text = getString(
                R.string.animal_and_flower_name,
                animalName,
                flowerName
            )
            val commonLetters = commonLetters(animalName, flowerName)
            tvNumberOfCommonLetters.text = commonLetters.length.toString().enNumberToFa()
            tvCommonLetters.text = getString(R.string.common_letters, commonLetters.addComma())

            vp2Images.adapter = AnimalAndFlowerImageAdapter(
                listOf(
                    args.animalAndFlower.animal.image,
                    args.animalAndFlower.flower.image
                )
            )

            TabLayoutMediator(tabLayout, vp2Images) { _, _ -> }.attach()
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
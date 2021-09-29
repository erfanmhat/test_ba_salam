package ir.erfan_mh_at.test_ba_salam.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ir.erfan_mh_at.test_ba_salam.databinding.FragmentHomeBinding
import ir.erfan_mh_at.test_ba_salam.other.Resource
import ir.erfan_mh_at.test_ba_salam.ui.viewModels.AnimalAndFlowerViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val animalAndFlowerViewModel: AnimalAndFlowerViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configure()
    }

    private fun configure() {
        animalAndFlowerViewModel.callAnimalAndFlowerApiAndHandleResource()
        animalAndFlowerViewModel.animalAndFlowerLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {

                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {

                }
            }
        }
    }
}
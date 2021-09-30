package ir.erfan_mh_at.test_ba_salam.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ir.erfan_mh_at.test_ba_salam.databinding.FragmentHomeBinding
import ir.erfan_mh_at.test_ba_salam.other.Resource
import ir.erfan_mh_at.test_ba_salam.ui.adapters.AnimalAndFlowerAdapter
import ir.erfan_mh_at.test_ba_salam.ui.viewModels.AnimalAndFlowerViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val animalAndFlowerViewModel: AnimalAndFlowerViewModel by viewModels()
    private lateinit var animalAndFlowerAdapter: AnimalAndFlowerAdapter
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
        observeToObservers()
    }

    private fun configure() {
        setupRecyclerView()
    }

    private fun observeToObservers() {
        animalAndFlowerViewModel.callAnimalAndFlowerApiAndHandleResource()
        animalAndFlowerViewModel.animalAndFlowerLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.pbLoading.visibility = View.GONE
                    animalAndFlowerAdapter.submitList(it.data ?: listOf())
                }
                is Resource.Loading -> {
                    binding.pbLoading.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.pbLoading.visibility = View.GONE
                    Toast.makeText(context, it.message ?: "error !", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        animalAndFlowerAdapter = AnimalAndFlowerAdapter()
        binding.rvAnimalAndFlower.apply {
            adapter = animalAndFlowerAdapter
            layoutManager = LinearLayoutManager(context)
        }
        animalAndFlowerAdapter.setCircleOnClickListener { animalAndFlower ->
            val action = HomeFragmentDirections.actionHomeFragmentToInfoFragment(animalAndFlower)
            findNavController().navigate(action)
        }
    }
}
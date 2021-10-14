package ir.erfan_mh_at.test_ba_salam.presentation.animal_and_flower_list

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ir.erfan_mh_at.test_ba_salam.R
import ir.erfan_mh_at.test_ba_salam.databinding.FragmentAnimalAndFlowerListBinding
import ir.erfan_mh_at.test_ba_salam.presentation.MainActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimalAndFlowerListFragment : Fragment() {

    private val animalAndFlowerViewModel: AnimalAndFlowerViewModel by hiltNavGraphViewModels(R.id.ba_salam_nav_graph)
    private lateinit var animalAndFlowerAdapter: AnimalAndFlowerAdapter
    private lateinit var binding: FragmentAnimalAndFlowerListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimalAndFlowerListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configure()
        collectAnimalAndFlowerStateFlow()
    }

    private fun configure() {
        setupRecyclerView()
        setOnClicks()
        setupSwipeRefreshLayout()
    }

    private fun collectAnimalAndFlowerStateFlow() = lifecycleScope.launch {
        animalAndFlowerViewModel.animalAndFlowerStateFlow.collect {
            when (it) {
                is AnimalAndFlowerListState.Success -> {
                    binding.pbLoading.visibility = View.GONE
                    animalAndFlowerAdapter.submitList(it.animalAndFlowerList)
                }
                is AnimalAndFlowerListState.Loading -> {
                    binding.pbLoading.visibility = View.VISIBLE
                }
                is AnimalAndFlowerListState.Error -> {
                    binding.pbLoading.visibility = View.GONE
                    Toast.makeText(context, it.error, Toast.LENGTH_LONG).show()
                }
                is AnimalAndFlowerListState.Empty -> Unit
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
            val action = AnimalAndFlowerListFragmentDirections
                .actionAnimalAndFlowerListFragmentToAnimalAndFlowerInfoFragment(animalAndFlower)
            findNavController().navigate(action)
        }
    }

    private fun setOnClicks() {
        val mainActivity = (activity as MainActivity)
        mainActivity.icMenuOnClickListener = {
            Toast.makeText(context, "home fragment menu!", Toast.LENGTH_LONG).show()
        }

        mainActivity.icSearchOnClickListener = {
            Toast.makeText(context, "home fragment search!", Toast.LENGTH_LONG).show()
        }

        mainActivity.icBackOnClickListener = {
            (activity as MainActivity).finish()
        }
    }

    private fun setupSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            animalAndFlowerViewModel.setupAnimalAndFlowerFlow()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }
}
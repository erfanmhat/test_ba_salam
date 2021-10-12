package ir.erfan_mh_at.test_ba_salam.presentation.animal_and_flower_list

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ir.erfan_mh_at.test_ba_salam.common.Resource
import ir.erfan_mh_at.test_ba_salam.databinding.FragmentAnimalAndFlowerListBinding
import ir.erfan_mh_at.test_ba_salam.presentation.MainActivity

class AnimalAndFlowerListFragment : Fragment() {

    private lateinit var animalAndFlowerViewModel: AnimalAndFlowerViewModel
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
        observeToObservers()
    }

    private fun configure() {
        animalAndFlowerViewModel = (activity as MainActivity).animalAndFlowerViewModel
        setupRecyclerView()
        setOnClicks()
        setupSwipeRefreshLayout()
    }

    private fun observeToObservers() {
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
            animalAndFlowerViewModel.callAnimalAndFlowerApiAndHandleResource()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }
}
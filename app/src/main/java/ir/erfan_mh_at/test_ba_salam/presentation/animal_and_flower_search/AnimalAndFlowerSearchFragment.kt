package ir.erfan_mh_at.test_ba_salam.presentation.animal_and_flower_search

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ir.erfan_mh_at.test_ba_salam.R
import ir.erfan_mh_at.test_ba_salam.common.Constants.SEARCH_TIME_DELAY
import ir.erfan_mh_at.test_ba_salam.databinding.FragmentAnimalAndFlowerSearchBinding
import ir.erfan_mh_at.test_ba_salam.presentation.MainActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimalAndFlowerSearchFragment : Fragment() {

    private val animalAndFlowerSearchViewModel: AnimalAndFlowerSearchViewModel by hiltNavGraphViewModels(
        R.id.ba_salam_nav_graph
    )

    private val animalAndFlowerSearchAdapter = AnimalAndFlowerSearchAdapter()

    private lateinit var searchView: SearchView

    private lateinit var binding: FragmentAnimalAndFlowerSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimalAndFlowerSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configure()
    }

    private fun configure() {
        setupRecyclerView()
        setOnClicks()
        collectAnimalAndFlowerSearchStateFlow()
        setupSearchView()
        setupOnQueryTextListener()
    }

    private fun collectAnimalAndFlowerSearchStateFlow() = lifecycleScope.launch {
        animalAndFlowerSearchViewModel.animalAndFlowerSearchStateFlow.collect {
            when (it) {
                is AnimalAndFlowerSearchState.Success -> {
                    binding.pbSearch.visibility = View.GONE
                    binding.tvNoItemFound.visibility = View.GONE
                    animalAndFlowerSearchAdapter.submitList(it.result)
                }
                is AnimalAndFlowerSearchState.Loading -> {
                    binding.pbSearch.visibility = View.VISIBLE
                    binding.tvNoItemFound.visibility = View.GONE
                }
                is AnimalAndFlowerSearchState.NoItemFound -> {
                    binding.pbSearch.visibility = View.GONE
                    binding.tvNoItemFound.visibility = View.VISIBLE
                    animalAndFlowerSearchAdapter.submitList(emptyList())
                }
                is AnimalAndFlowerSearchState.Error -> {
                    binding.pbSearch.visibility = View.GONE
                    binding.tvNoItemFound.visibility = View.GONE
                    Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
                }
                is AnimalAndFlowerSearchState.Empty -> {
                    binding.pbSearch.visibility = View.GONE
                    binding.tvNoItemFound.visibility = View.GONE
                    animalAndFlowerSearchAdapter.submitList(emptyList())
                }
            }
        }
    }

    private fun setupOnQueryTextListener() {
        var searchDelayJob: Job? = null
        var searchJob: Job? = null
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                activity?.let { hideKeyboard(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // if recently opened Fragment then onQueryTextChange should not be called
                if (animalAndFlowerSearchViewModel.isRecentlyOpenedFragment) return true
                searchDelayJob?.cancel()
                searchJob?.cancel()
                searchDelayJob = lifecycleScope.launch {
                    delay(SEARCH_TIME_DELAY)
                    newText?.let { query ->
                        animalAndFlowerSearchViewModel.query = query
                        animalAndFlowerSearchViewModel.search().also { job ->
                            searchJob = job
                        }
                    }
                }
                searchDelayJob?.start()
                return true
            }
        })
    }

    private fun setupRecyclerView() {
        binding.rvSearch.apply {
            adapter = animalAndFlowerSearchAdapter
            layoutManager = LinearLayoutManager(context)
        }
        animalAndFlowerSearchAdapter.setCircleOnClickListener { animalAndFlower ->
            findNavController().currentDestination?.id?.let { DestinationId ->
                val action = AnimalAndFlowerSearchFragmentDirections
                    .actionAnimalAndFlowerSearchFragmentToAnimalAndFlowerInfoFragment(
                        animalAndFlower, DestinationId
                    )
                activity?.let { hideKeyboard(it) }
                findNavController().navigate(action)
            }
        }
    }

    private fun setOnClicks() {
        (activity as MainActivity).icBackOnClickListener = {
            findNavController().navigate(R.id.action_animalAndFlowerSearchFragment_to_animalAndFlowerListFragment)
        }
    }

    private fun setupSearchView() {
        searchView = (activity as MainActivity).binding.searchView
        searchView.apply {
            // if recently opened Fragment then set query from viewModel
            if (animalAndFlowerSearchViewModel.isRecentlyOpenedFragment) setQuery(
                animalAndFlowerSearchViewModel.query, false
            )
            // focus on SearchView
            isFocusable = true
            isIconified = false
            requestFocusFromTouch()
            // open keyboard if query is empty
            if (query.isEmpty()) {
                lifecycleScope.launch {
                    delay(1000)
                    activity?.let { showKeyboard(it) }
                }
            }
            // next lines prevent call SearchView.onQueryTextChange function when recently opened Fragment
            animalAndFlowerSearchViewModel.isRecentlyOpenedFragment = true
            lifecycleScope.launch {
                delay(1000)
                animalAndFlowerSearchViewModel.isRecentlyOpenedFragment = false
            }
        }
    }

    private fun hideKeyboard(activity: Activity) {
        val view = activity.currentFocus
        val methodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        assert(view != null)
        methodManager.hideSoftInputFromWindow(
            view!!.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    private fun showKeyboard(activity: Activity) {
        val view = activity.currentFocus
        val methodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        assert(view != null)
        methodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}
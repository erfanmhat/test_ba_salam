package ir.erfan_mh_at.test_ba_salam.presentation.animal_and_flower_search

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ir.erfan_mh_at.test_ba_salam.R
import ir.erfan_mh_at.test_ba_salam.common.Constants.SEARCH_TIME_DELAY
import ir.erfan_mh_at.test_ba_salam.databinding.FragmentAnimalAndFlowerSearchBinding
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
        collectAnimalAndFlowerSearchStateFlow()
        setupOnQueryTextListener()
        setupRecyclerView()
        binding.searchView.isFocusable = true
        binding.searchView.isIconified = false
        binding.searchView.requestFocusFromTouch()
        lifecycleScope.launch {
            delay(1000)
            activity?.let { showKeyboard(it) }
        }
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
        var job: Job? = null
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                activity?.let { hideKeyboard(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                job?.cancel()
                job = lifecycleScope.launch {
                    delay(SEARCH_TIME_DELAY)
                    newText?.let {
                        animalAndFlowerSearchViewModel.search(newText)
                    }
                }
                job?.start()
                return true
            }
        })
    }

    private fun setupRecyclerView() {
        binding.rvSearch.apply {
            adapter = animalAndFlowerSearchAdapter
            layoutManager = LinearLayoutManager(context)
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
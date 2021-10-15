package ir.erfan_mh_at.test_ba_salam.presentation.animal_and_flower_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.erfan_mh_at.test_ba_salam.common.Resource
import ir.erfan_mh_at.test_ba_salam.domain.use_case.SearchAnimalAndFlowerUseCase
import ir.erfan_mh_at.test_ba_salam.presentation.animal_and_flower_list.AnimalAndFlowerListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimalAndFlowerSearchViewModel @Inject constructor(
    private val searchAnimalAndFlowerUseCase: SearchAnimalAndFlowerUseCase
) : ViewModel() {

    private val _searchAnimalAndFlowerStateFlow: MutableStateFlow<SearchAnimalAndFlowerState> =
        MutableStateFlow(SearchAnimalAndFlowerState.Empty)
    val searchAnimalAndFlowerStateFlow =
        _searchAnimalAndFlowerStateFlow as StateFlow<SearchAnimalAndFlowerState>

    fun search(query: String) = viewModelScope.launch {
        searchAnimalAndFlowerUseCase.execute(query).collect {
            _searchAnimalAndFlowerStateFlow.emit(
                when (it) {
                    is Resource.Success -> {
                        if (it.data.isNullOrEmpty()) {
                            SearchAnimalAndFlowerState.Success(
                                it.data ?: emptyList()
                            )
                        } else {
                            SearchAnimalAndFlowerState.NoItemFound
                        }
                    }
                    is Resource.Loading -> SearchAnimalAndFlowerState.Loading
                    is Resource.Error -> SearchAnimalAndFlowerState.Error(
                        it.message ?: "an unexpected error !"
                    )
                }
            )
        }
    }
}
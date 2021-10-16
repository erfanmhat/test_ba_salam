package ir.erfan_mh_at.test_ba_salam.presentation.animal_and_flower_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.erfan_mh_at.test_ba_salam.common.Resource
import ir.erfan_mh_at.test_ba_salam.domain.use_case.SearchAnimalAndFlowerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimalAndFlowerSearchViewModel @Inject constructor(
    private val searchAnimalAndFlowerUseCase: SearchAnimalAndFlowerUseCase
) : ViewModel() {

    private val _animalAndFlowerSearchStateFlow: MutableStateFlow<AnimalAndFlowerSearchState> =
        MutableStateFlow(AnimalAndFlowerSearchState.Empty)
    val animalAndFlowerSearchStateFlow =
        _animalAndFlowerSearchStateFlow as StateFlow<AnimalAndFlowerSearchState>

    var query: String = ""

    fun search() = viewModelScope.launch {
        if (query.isEmpty()) {
            _animalAndFlowerSearchStateFlow.emit(AnimalAndFlowerSearchState.Empty)
            return@launch
        }
        searchAnimalAndFlowerUseCase.execute("%$query%").collect {
            _animalAndFlowerSearchStateFlow.emit(
                when (it) {
                    is Resource.Success -> {
                        if (!it.data.isNullOrEmpty()) {
                            AnimalAndFlowerSearchState.Success(it.data)
                        } else {
                            AnimalAndFlowerSearchState.NoItemFound
                        }
                    }
                    is Resource.Loading -> AnimalAndFlowerSearchState.Loading
                    is Resource.Error -> AnimalAndFlowerSearchState.Error(
                        it.message ?: "an unexpected error !"
                    )
                }
            )
        }
    }
}
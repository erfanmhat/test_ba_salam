package ir.erfan_mh_at.test_ba_salam.presentation.animal_and_flower_list

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.erfan_mh_at.test_ba_salam.common.Resource
import ir.erfan_mh_at.test_ba_salam.domain.use_case.GetAnimalAndFlowerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimalAndFlowerViewModel @Inject constructor(
    private val getAnimalAndFlowerUseCase: GetAnimalAndFlowerUseCase
) : ViewModel(), LifecycleObserver {

    private val _animalAndFlowerMutableStateFlow: MutableStateFlow<AnimalAndFlowerListState> =
        MutableStateFlow(AnimalAndFlowerListState.Empty)
    val animalAndFlowerStateFlow =
        _animalAndFlowerMutableStateFlow as StateFlow<AnimalAndFlowerListState>

    init {
        setupAnimalAndFlowerFlow()
    }

    fun setupAnimalAndFlowerFlow() = viewModelScope.launch {
        getAnimalAndFlowerUseCase.execute().collect {
            _animalAndFlowerMutableStateFlow.emit(
                when (it) {
                    is Resource.Success -> AnimalAndFlowerListState.Success(it.data ?: emptyList())
                    is Resource.Loading -> AnimalAndFlowerListState.Loading
                    is Resource.Error -> AnimalAndFlowerListState.Error(
                        it.message ?: "an unexpected error !"
                    )
                }
            )
        }
    }
}
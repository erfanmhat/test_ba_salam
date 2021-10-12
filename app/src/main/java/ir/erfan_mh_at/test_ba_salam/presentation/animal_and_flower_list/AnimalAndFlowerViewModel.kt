package ir.erfan_mh_at.test_ba_salam.presentation.animal_and_flower_list

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.erfan_mh_at.test_ba_salam.domain.use_case.GetAnimalsUseCase
import ir.erfan_mh_at.test_ba_salam.domain.use_case.GetFlowersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimalAndFlowerViewModel @Inject constructor(
    private val getAnimalsUseCase: GetAnimalsUseCase,
    private val getFlowersUseCase: GetFlowersUseCase
) : ViewModel() {

    private val _animalAndFlowerMutableStateFlow: MutableStateFlow<AnimalAndFlowerListState> =
        MutableStateFlow(AnimalAndFlowerListState.Empty)
    val animalAndFlowerStateFlow =
        _animalAndFlowerMutableStateFlow as StateFlow<AnimalAndFlowerListState>

    init {
        callAnimalAndFlowerApiAndHandleResource()
    }

    fun callAnimalAndFlowerApiAndHandleResource() = viewModelScope.launch {
        _animalAndFlowerMutableStateFlow.emit(AnimalAndFlowerListState.Loading)
        getAnimalsUseCase.execute()
        getFlowersUseCase.execute()
    }
}
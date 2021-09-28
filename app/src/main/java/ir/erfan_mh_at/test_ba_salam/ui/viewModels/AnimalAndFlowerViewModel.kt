package ir.erfan_mh_at.test_ba_salam.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.erfan_mh_at.test_ba_salam.other.MainRepository
import kotlinx.coroutines.launch


class AnimalAndFlowerViewModel(mainRepository: MainRepository) : ViewModel() {



    fun callApis() = viewModelScope.launch {
        safeCallApis()
    }

    private suspend fun safeCallApis() {

    }

}
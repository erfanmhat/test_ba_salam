package ir.erfan_mh_at.test_ba_salam.presentation.animal_and_flower_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.erfan_mh_at.test_ba_salam.common.Resource
import ir.erfan_mh_at.test_ba_salam.common.mergeAnimalAndFlowerList
import ir.erfan_mh_at.test_ba_salam.common.safeCallApi
import ir.erfan_mh_at.test_ba_salam.data.remote.dto.toAnimal
import ir.erfan_mh_at.test_ba_salam.data.remote.dto.toFlower
import ir.erfan_mh_at.test_ba_salam.data.repository.MainRepository
import ir.erfan_mh_at.test_ba_salam.domain.model.AnimalAndFlower
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimalAndFlowerViewModel @Inject constructor(
    app: Application,
    private val mainRepository: MainRepository
) : AndroidViewModel(app) {

    private val _animalAndFlowerMutableLiveData: MutableLiveData<Resource<List<AnimalAndFlower>>> =
        MutableLiveData()
    val animalAndFlowerLiveData =
        _animalAndFlowerMutableLiveData as LiveData<Resource<List<AnimalAndFlower>>>

    init {
        callAnimalAndFlowerApiAndHandleResource()
    }

    fun callAnimalAndFlowerApiAndHandleResource() = viewModelScope.launch {
        _animalAndFlowerMutableLiveData.postValue(Resource.Loading())
        val animalDeferred = async {
            safeCallApi(
                getApplication<Application>()
            ) { mainRepository.getAnimals() }
        }
        val flowerDeferred = async {
            safeCallApi(
                getApplication<Application>()
            ) { mainRepository.getFlowers() }
        }
        val animalResource = animalDeferred.await()
        val flowerResource = flowerDeferred.await()
        when (animalResource) {
            is Resource.Error -> {
                _animalAndFlowerMutableLiveData.postValue(
                    Resource.Error(
                        animalResource.message ?: "error !"
                    )
                )
            }
            is Resource.Success -> {
                when (flowerResource) {
                    is Resource.Error -> {
                        _animalAndFlowerMutableLiveData.postValue(
                            Resource.Error(
                                flowerResource.message ?: "error !"
                            )
                        )
                    }
                    is Resource.Success -> {
                        _animalAndFlowerMutableLiveData.postValue(
                            if (animalResource.data != null && flowerResource.data != null) {
                                Resource.Success(
                                    mergeAnimalAndFlowerList(
                                        animalResource.data.data.map { it.toAnimal() },
                                        flowerResource.data.data.map { it.toFlower() }
                                    )
                                )
                            } else {
                                Resource.Error("null resource")
                            }
                        )
                    }
                    else -> Unit
                }
            }
            else -> Unit
        }
    }
}
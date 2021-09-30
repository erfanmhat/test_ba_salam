package ir.erfan_mh_at.test_ba_salam.ui.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.erfan_mh_at.test_ba_salam.data.repositorys.MainRepository
import ir.erfan_mh_at.test_ba_salam.data.models.Animal
import ir.erfan_mh_at.test_ba_salam.data.models.Flower
import ir.erfan_mh_at.test_ba_salam.other.*
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimalAndFlowerViewModel @Inject constructor(
    app: Application,
    private val mainRepository: MainRepository
) : AndroidViewModel(app) {

    private val _animalAndFlowerMutableLiveData: MutableLiveData<Resource<List<Pair<Animal, Flower>>>> =
        MutableLiveData()
    val animalAndFlowerLiveData =
        _animalAndFlowerMutableLiveData as LiveData<Resource<List<Pair<Animal, Flower>>>>

    fun callAnimalAndFlowerApiAndHandleResource() = viewModelScope.launch {
        _animalAndFlowerMutableLiveData.postValue(Resource.Loading())
        val animalDeferred = async {
            safeCallApi(
                getApplication<Application>(),
                mainRepository.getAnimals()
            )
        }
        val flowerDeferred = async {
            safeCallApi(
                getApplication<Application>(),
                mainRepository.getFlowers()
            )
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
                                        animalResource.data.data,
                                        flowerResource.data.data
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

    private fun mergeAnimalAndFlowerList(
        animalList: List<Animal>,
        flowerList: List<Flower>
    ): List<Pair<Animal, Flower>> {
        val result: MutableList<Pair<Animal, Flower>> = mutableListOf()
        for (animal in animalList) {
            val flower = flowerList.find { flower -> animal.id == flower.id }
            if (flower != null) result.add(Pair(animal, flower))
        }
        return result
    }
}
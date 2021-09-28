package ir.erfan_mh_at.test_ba_salam.ui.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ir.erfan_mh_at.test_ba_salam.models.Animal
import ir.erfan_mh_at.test_ba_salam.models.Flower
import ir.erfan_mh_at.test_ba_salam.other.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class AnimalAndFlowerViewModel(app: Application, private val mainRepository: MainRepository) :
    AndroidViewModel(app) {

    private val _animalAndFlowerMutableLiveData: MutableLiveData<Resource<List<Pair<Animal, Flower>>>> =
        MutableLiveData()

    fun callAnimalAndFlowerApi() = viewModelScope.launch {
        _animalAndFlowerMutableLiveData
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
                                    mergeResourceAnimalAndFlower(animalResource, flowerResource)
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

    private fun mergeResourceAnimalAndFlower(
        animalResource: Resource.Success<ListResponse<Animal>>,
        flowerResource: Resource.Success<ListResponse<Flower>>
    ): List<Pair<Animal, Flower>> {
        val result: MutableList<Pair<Animal, Flower>> = mutableListOf()
        for (animal in animalResource.data!!.data) {
            val flowerList = flowerResource.data!!.data.filter { flower -> animal.id == flower.id }
            result.add(Pair(animal, flowerList[0]))
        }
        return result
    }
}
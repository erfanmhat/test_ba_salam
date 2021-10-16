package ir.erfan_mh_at.test_ba_salam.data.repository

import ir.erfan_mh_at.test_ba_salam.common.Constants.QUERY_KEY_ANIMAL
import ir.erfan_mh_at.test_ba_salam.common.Constants.QUERY_KEY_FLOWER
import ir.erfan_mh_at.test_ba_salam.common.mergeAnimalAndFlowerList
import ir.erfan_mh_at.test_ba_salam.common.shouldUpdateTheInformationFromRemote
import ir.erfan_mh_at.test_ba_salam.data.database.AnimalAndFlowerDAO
import ir.erfan_mh_at.test_ba_salam.data.database.dto.AnimalAndFlowerLocalDto
import ir.erfan_mh_at.test_ba_salam.data.remote.BaSalamApi
import ir.erfan_mh_at.test_ba_salam.data.remote.dto.toAnimalLocalDto
import ir.erfan_mh_at.test_ba_salam.data.remote.dto.toFlowerLocalDto
import ir.erfan_mh_at.test_ba_salam.domain.repository.MainRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val api: BaSalamApi,
    private val animalAndFlowerDAO: AnimalAndFlowerDAO,
) : MainRepository {
    override suspend fun getAnimalAndFlower(): List<AnimalAndFlowerLocalDto> {
        val animalAndFlowerLocal = animalAndFlowerDAO.getAnimalAndFlower()
        return if (animalAndFlowerLocal.isNotEmpty()) {
            if (shouldUpdateTheInformationFromRemote(animalAndFlowerLocal[0].timestamp)) {
                getAnimalAndFlowerFromAPIAndSaveToDatabase()
            } else {
                animalAndFlowerLocal
            }
        } else {
            getAnimalAndFlowerFromAPIAndSaveToDatabase()
        }
    }

    private suspend fun getAnimalAndFlowerFromAPIAndSaveToDatabase(): List<AnimalAndFlowerLocalDto> {
        var animalAndFlowerList: List<AnimalAndFlowerLocalDto> = emptyList()
        callAnimalAndFlowerAPI {
            animalAndFlowerList = it
        }
        for (item in animalAndFlowerList) {
            item.id = item.animal.id //this line makes replace old information
            // with a new information (if there is an old information)
            animalAndFlowerDAO.upsertAnimalAndFlower(item)
        }
        return animalAndFlowerDAO.getAnimalAndFlower()
    }

    private suspend fun callAnimalAndFlowerAPI(resultCallBack: (List<AnimalAndFlowerLocalDto>) -> Unit) {
        withContext(Dispatchers.IO) {
            val animalDeferred = async(Dispatchers.IO) { api.getAnimals(QUERY_KEY_ANIMAL) }
            val flowerDeferred = async(Dispatchers.IO) { api.getFlowers(QUERY_KEY_FLOWER) }
            val animalResponse = animalDeferred.await()
            val flowerResponse = flowerDeferred.await()
            resultCallBack(
                mergeAnimalAndFlowerList(
                    animalResponse.data.map { it.toAnimalLocalDto() },
                    flowerResponse.data.map { it.toFlowerLocalDto() }
                )
            )
        }
    }

    override suspend fun searchAnimalAndFlower(query: String): List<AnimalAndFlowerLocalDto> =
        animalAndFlowerDAO.searchAnimalAndFlower(query)
}

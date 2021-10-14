package ir.erfan_mh_at.test_ba_salam.data.repository

import ir.erfan_mh_at.test_ba_salam.common.Constants.QUERY_KEY_ANIMAL
import ir.erfan_mh_at.test_ba_salam.common.Constants.QUERY_KEY_FLOWER
import ir.erfan_mh_at.test_ba_salam.common.mergeAnimalAndFlowerList
import ir.erfan_mh_at.test_ba_salam.data.database.BaSalamDatabase
import ir.erfan_mh_at.test_ba_salam.data.database.dto.AnimalAndFlowerLocalDto
import ir.erfan_mh_at.test_ba_salam.data.remote.BaSalamApi
import ir.erfan_mh_at.test_ba_salam.data.remote.dto.toAnimalLocalDto
import ir.erfan_mh_at.test_ba_salam.data.remote.dto.toFlowerLocalDto
import ir.erfan_mh_at.test_ba_salam.domain.repository.MainRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val api: BaSalamApi,
    private val db: BaSalamDatabase
) : MainRepository {
    override suspend fun getAnimalAndFlower(): List<AnimalAndFlowerLocalDto> {
        val animalAndFlowerLocal = db.getDao().getAnimalAndFlower()
        if (animalAndFlowerLocal.isNotEmpty()) {
            return animalAndFlowerLocal
        } else {
            callAnimalAndFlowerAPI { animalAndFlowerList ->
                for (item in animalAndFlowerList) {
                    db.getDao().upsertAnimalAndFlower(item)
                }
            }
        }
        return db.getDao().getAnimalAndFlower()
    }

    private suspend fun callAnimalAndFlowerAPI(resultCallBack: suspend (List<AnimalAndFlowerLocalDto>) -> Unit) {
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
}

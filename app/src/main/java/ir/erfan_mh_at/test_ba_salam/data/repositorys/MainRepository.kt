package ir.erfan_mh_at.test_ba_salam.data.repositorys

import ir.erfan_mh_at.test_ba_salam.data.api.RetrofitInstance.Companion.BA_SALAM_API
import ir.erfan_mh_at.test_ba_salam.other.Constants
import ir.erfan_mh_at.test_ba_salam.other.Constants.QUERY_KEY_ANIMAL
import ir.erfan_mh_at.test_ba_salam.other.Constants.QUERY_KEY_FLOWER
import javax.inject.Inject

class MainRepository @Inject constructor() {

    suspend fun getAnimals() =
        BA_SALAM_API.getAnimals(QUERY_KEY_ANIMAL)

    suspend fun getFlowers() =
        BA_SALAM_API.getFlowers(QUERY_KEY_FLOWER)
}
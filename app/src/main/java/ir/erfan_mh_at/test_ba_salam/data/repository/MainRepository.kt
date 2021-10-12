package ir.erfan_mh_at.test_ba_salam.data.repository

import ir.erfan_mh_at.test_ba_salam.data.remote.RetrofitInstance.Companion.BA_SALAM_API
import ir.erfan_mh_at.test_ba_salam.common.Constants.QUERY_KEY_ANIMAL
import ir.erfan_mh_at.test_ba_salam.common.Constants.QUERY_KEY_FLOWER
import javax.inject.Inject

class MainRepository @Inject constructor() {

    suspend fun getAnimals() =
        BA_SALAM_API.getAnimals(QUERY_KEY_ANIMAL)

    suspend fun getFlowers() =
        BA_SALAM_API.getFlowers(QUERY_KEY_FLOWER)
}
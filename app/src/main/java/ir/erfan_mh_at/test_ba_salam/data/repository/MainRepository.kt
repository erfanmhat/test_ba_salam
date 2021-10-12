package ir.erfan_mh_at.test_ba_salam.data.repository

import ir.erfan_mh_at.test_ba_salam.common.Constants.QUERY_KEY_ANIMAL
import ir.erfan_mh_at.test_ba_salam.common.Constants.QUERY_KEY_FLOWER
import ir.erfan_mh_at.test_ba_salam.data.remote.BaSalamApi
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: BaSalamApi
) {
    suspend fun getAnimals() = api.getAnimals(QUERY_KEY_ANIMAL)

    suspend fun getFlowers() = api.getFlowers(QUERY_KEY_FLOWER)
}
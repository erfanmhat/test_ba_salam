package ir.erfan_mh_at.test_ba_salam.data.repository

import ir.erfan_mh_at.test_ba_salam.common.Constants.QUERY_KEY_ANIMAL
import ir.erfan_mh_at.test_ba_salam.common.Constants.QUERY_KEY_FLOWER
import ir.erfan_mh_at.test_ba_salam.data.remote.BaSalamApi
import ir.erfan_mh_at.test_ba_salam.data.remote.response.GetAnimalsResponse
import ir.erfan_mh_at.test_ba_salam.data.remote.response.GetFlowersResponse
import ir.erfan_mh_at.test_ba_salam.domain.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val api: BaSalamApi
) : MainRepository {
    override suspend fun getAnimals(): GetAnimalsResponse = api.getAnimals(QUERY_KEY_ANIMAL)

    override suspend fun getFlowers(): GetFlowersResponse = api.getFlowers(QUERY_KEY_FLOWER)
}
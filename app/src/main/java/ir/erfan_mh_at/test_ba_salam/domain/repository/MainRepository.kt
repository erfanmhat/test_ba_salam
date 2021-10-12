package ir.erfan_mh_at.test_ba_salam.domain.repository

import ir.erfan_mh_at.test_ba_salam.data.remote.response.GetAnimalsResponse
import ir.erfan_mh_at.test_ba_salam.data.remote.response.GetFlowersResponse

interface MainRepository {
    suspend fun getAnimals(): GetAnimalsResponse

    suspend fun getFlowers(): GetFlowersResponse
}
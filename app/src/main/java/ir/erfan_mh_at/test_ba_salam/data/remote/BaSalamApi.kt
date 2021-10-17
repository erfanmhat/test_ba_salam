package ir.erfan_mh_at.test_ba_salam.data.remote

import ir.erfan_mh_at.test_ba_salam.data.remote.response.GetAnimalsResponse
import ir.erfan_mh_at.test_ba_salam.data.remote.response.GetFlowersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BaSalamApi {

    @GET("intern.android")
    suspend fun getAnimals(
        @Query("kind")
        kind: String
    ): GetAnimalsResponse

    @GET("intern.android")
    suspend fun getFlowers(
        @Query("kind")
        kind: String
    ): GetFlowersResponse
}
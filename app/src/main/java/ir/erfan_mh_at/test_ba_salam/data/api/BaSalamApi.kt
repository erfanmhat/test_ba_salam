package ir.erfan_mh_at.test_ba_salam.data.api

import ir.erfan_mh_at.test_ba_salam.data.api.responses.AnimalResponse
import ir.erfan_mh_at.test_ba_salam.data.api.responses.FlowerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BaSalamApi {

    @GET("intern.android")
    suspend fun getAnimals(
        @Query("kind")
        kind: String
    ): Response<AnimalResponse>

    @GET("intern.android")
    suspend fun getFlowers(
        @Query("kind")
        kind: String
    ): Response<FlowerResponse>
}
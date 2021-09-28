package ir.erfan_mh_at.test_ba_salam.other

import ir.erfan_mh_at.test_ba_salam.models.Animal
import ir.erfan_mh_at.test_ba_salam.models.Flower
import retrofit2.Response

class MainRepository {

    suspend fun getAnimals(): Response<ListResponse<Animal>> {
        TODO()
    }

    suspend fun getFlowers(): Response<ListResponse<Flower>> {
        TODO()
    }
}
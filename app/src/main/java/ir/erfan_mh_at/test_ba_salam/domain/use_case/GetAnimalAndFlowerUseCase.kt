package ir.erfan_mh_at.test_ba_salam.domain.use_case

import ir.erfan_mh_at.test_ba_salam.common.Resource
import ir.erfan_mh_at.test_ba_salam.data.database.dto.toAnimalAndFlower
import ir.erfan_mh_at.test_ba_salam.domain.model.AnimalAndFlower
import ir.erfan_mh_at.test_ba_salam.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAnimalAndFlowerUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    fun execute(): Flow<Resource<List<AnimalAndFlower>>> = flow {
        try {
            emit(Resource.Loading())
            val result = mainRepository.getAnimalAndFlower()
            emit(
                Resource.Success(
                    result.map { it.toAnimalAndFlower() }
                )
            )
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "an unexpected error !"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection !"))
        } catch (e: Exception) {
            emit(Resource.Error("an unexpected error !"))
        }
    }
}
package ir.erfan_mh_at.test_ba_salam.domain.use_case

import ir.erfan_mh_at.test_ba_salam.common.Resource
import ir.erfan_mh_at.test_ba_salam.data.database.dto.toAnimalAndFlower
import ir.erfan_mh_at.test_ba_salam.domain.model.AnimalAndFlower
import ir.erfan_mh_at.test_ba_salam.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class SearchAnimalAndFlowerUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    fun execute(query: String): Flow<Resource<List<AnimalAndFlower>>> = flow {
        emit(Resource.Loading())
        try{
            val result = mainRepository.searchAnimalAndFlower(query)
            emit(
                Resource.Success(
                    result.map { it.toAnimalAndFlower() }
                )
            )
        }catch (e:Exception){
            emit(Resource.Error("an unexpected error !"))
        }
    }
}
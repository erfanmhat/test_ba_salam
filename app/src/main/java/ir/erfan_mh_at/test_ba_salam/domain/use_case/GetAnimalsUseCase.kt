package ir.erfan_mh_at.test_ba_salam.domain.use_case

import ir.erfan_mh_at.test_ba_salam.common.handleRemoteResponse
import ir.erfan_mh_at.test_ba_salam.data.remote.dto.toAnimal
import ir.erfan_mh_at.test_ba_salam.domain.repository.MainRepository
import javax.inject.Inject

class GetAnimalsUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    fun execute() = handleRemoteResponse {
        mainRepository.getAnimals().data.map { it.toAnimal() }
    }
}
package ir.erfan_mh_at.test_ba_salam.domain.use_case

import ir.erfan_mh_at.test_ba_salam.common.handleRemoteResponse
import ir.erfan_mh_at.test_ba_salam.data.remote.dto.toFlower
import ir.erfan_mh_at.test_ba_salam.domain.repository.MainRepository
import javax.inject.Inject

class GetFlowersUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    fun execute() = handleRemoteResponse {
        mainRepository.getFlowers().data.map { it.toFlower() }
    }
}
package ir.erfan_mh_at.test_ba_salam.data.remote.response

import ir.erfan_mh_at.test_ba_salam.data.remote.dto.AnimalDto

data class GetAnimalsResponse(
    val data: List<AnimalDto>
)
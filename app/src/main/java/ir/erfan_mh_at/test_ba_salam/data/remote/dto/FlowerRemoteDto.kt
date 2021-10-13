package ir.erfan_mh_at.test_ba_salam.data.remote.dto

import ir.erfan_mh_at.test_ba_salam.data.database.dto.FlowerLocalDto

data class FlowerRemoteDto(
    val id: Int,
    val name: String,
    val image: String
)

fun FlowerRemoteDto.toFlowerLocalDto() = FlowerLocalDto(
    id = this.id,
    name = this.name,
    image = this.image
)
package ir.erfan_mh_at.test_ba_salam.data.remote.dto

import ir.erfan_mh_at.test_ba_salam.domain.model.Flower

data class FlowerRemoteDto(
    val id: Int,
    val name: String,
    val image: String
)

fun FlowerRemoteDto.toFlower() = Flower(
    id = this.id,
    name = this.name,
    image = this.image
)
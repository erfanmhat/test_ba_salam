package ir.erfan_mh_at.test_ba_salam.data.database.dto

import ir.erfan_mh_at.test_ba_salam.domain.model.Flower

data class FlowerLocalDto(
    val id: Int,
    val name: String,
    val image: String
)

fun FlowerLocalDto.toFlower() = Flower(
    id = this.id,
    name = this.name,
    image = this.image
)
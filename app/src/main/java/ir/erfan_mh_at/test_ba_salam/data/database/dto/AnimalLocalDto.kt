package ir.erfan_mh_at.test_ba_salam.data.database.dto

import ir.erfan_mh_at.test_ba_salam.domain.model.Animal

data class AnimalLocalDto(
    val id: Int,
    val name: String,
    val image: String
)

fun AnimalLocalDto.toAnimal() = Animal(
    id = this.id,
    name = this.name,
    image = this.image
)
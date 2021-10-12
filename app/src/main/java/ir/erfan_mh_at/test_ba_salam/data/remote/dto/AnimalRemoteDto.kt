package ir.erfan_mh_at.test_ba_salam.data.remote.dto

import ir.erfan_mh_at.test_ba_salam.domain.model.Animal

data class AnimalRemoteDto(
    val id: Int,
    val name: String,
    val image: String
)

fun AnimalRemoteDto.toAnimal() = Animal(
    id = this.id,
    name = this.name,
    image = this.image
)
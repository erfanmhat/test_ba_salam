package ir.erfan_mh_at.test_ba_salam.data.remote.dto

import ir.erfan_mh_at.test_ba_salam.data.database.dto.AnimalLocalDto

data class AnimalRemoteDto(
    val id: Int,
    val name: String,
    val image: String
)

fun AnimalRemoteDto.toAnimalLocalDto() = AnimalLocalDto(
    id = this.id,
    name = this.name,
    image = this.image
)
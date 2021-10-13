package ir.erfan_mh_at.test_ba_salam.domain.repository

import ir.erfan_mh_at.test_ba_salam.data.database.dto.AnimalAndFlowerLocalDto

interface MainRepository {
    suspend fun getAnimalAndFlower(): List<AnimalAndFlowerLocalDto>
}
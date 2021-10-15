package ir.erfan_mh_at.test_ba_salam.data.database.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.erfan_mh_at.test_ba_salam.domain.model.AnimalAndFlower

@Entity(tableName = "animal_and_flower_table")
data class AnimalAndFlowerLocalDto(
    val animal: AnimalLocalDto,
    val flower: FlowerLocalDto,
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val timestamp: Long = System.currentTimeMillis()
)

fun AnimalAndFlowerLocalDto.toAnimalAndFlower() = AnimalAndFlower(
    animal = this.animal.toAnimal(),
    flower = this.flower.toFlower()
)
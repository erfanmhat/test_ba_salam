package ir.erfan_mh_at.test_ba_salam.data.database

import androidx.room.*
import ir.erfan_mh_at.test_ba_salam.data.database.dto.AnimalAndFlowerDto

@Dao
interface AnimalAndFlowerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertAnimalAndFlower(animalAndFlower: AnimalAndFlowerDto)

    @Delete
    fun deleteAnimalAndFlower(animalAndFlower: AnimalAndFlowerDto)

    @Query("SELECT * FROM animal_and_flower_table")
    fun getAnimalAndFlower(): List<AnimalAndFlowerDto>
}
package ir.erfan_mh_at.test_ba_salam.data.database

import androidx.room.*
import ir.erfan_mh_at.test_ba_salam.data.database.dto.AnimalAndFlowerLocalDto

@Dao
interface AnimalAndFlowerDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAnimalAndFlower(animalAndFlowerLocal: AnimalAndFlowerLocalDto)

    @Delete
    suspend fun deleteAnimalAndFlower(animalAndFlowerLocal: AnimalAndFlowerLocalDto)

    @Query("SELECT * FROM animal_and_flower_table")
    suspend fun getAnimalAndFlower(): List<AnimalAndFlowerLocalDto>

    @Query("SELECT * FROM animal_and_flower_table WHERE animal_name LIKE :query OR flower_name LIKE :query")
    suspend fun searchAnimalAndFlower(query: String): List<AnimalAndFlowerLocalDto>
}
package ir.erfan_mh_at.test_ba_salam.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import ir.erfan_mh_at.test_ba_salam.data.database.dto.AnimalLocalDto
import ir.erfan_mh_at.test_ba_salam.data.database.dto.FlowerLocalDto


class DatabaseConverter {

    @TypeConverter
    fun toAnimal(json: String): AnimalLocalDto = Gson().fromJson(json, AnimalLocalDto::class.java)

    @TypeConverter
    fun fromAnimal(animal: AnimalLocalDto): String = Gson().toJson(animal)

    @TypeConverter
    fun toFlower(json: String): FlowerLocalDto = Gson().fromJson(json, FlowerLocalDto::class.java)

    @TypeConverter
    fun fromFlower(flower: FlowerLocalDto): String = Gson().toJson(flower)
}
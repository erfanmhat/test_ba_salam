package ir.erfan_mh_at.test_ba_salam.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.erfan_mh_at.test_ba_salam.data.database.dto.AnimalAndFlowerDto

@Database(
    entities = [AnimalAndFlowerDto::class],
    version = 1
)
@TypeConverters(DatabaseConverter::class)
abstract class BaSalamDatabase : RoomDatabase() {

    abstract fun getDao(): AnimalAndFlowerDao
}
package ir.erfan_mh_at.test_ba_salam.common

import java.util.concurrent.TimeUnit

object Constants {
    const val BASE_URL = "https://kashkool.basalam.com"
    const val BA_SALAM_DATABASE_NAME = "ba_salam_db"
    const val QUERY_KEY_ANIMAL = "animal"
    const val QUERY_KEY_FLOWER = "flower"
    const val SEARCH_TIME_DELAY = 800L
    val FRESH_TIMEOUT = TimeUnit.HOURS.toMillis(1)
}
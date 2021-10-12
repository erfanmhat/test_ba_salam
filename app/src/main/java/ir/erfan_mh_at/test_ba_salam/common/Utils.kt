package ir.erfan_mh_at.test_ba_salam.common

import ir.erfan_mh_at.test_ba_salam.domain.model.Animal
import ir.erfan_mh_at.test_ba_salam.domain.model.AnimalAndFlower
import ir.erfan_mh_at.test_ba_salam.domain.model.Flower
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

fun <T> handleRemoteResponse(callAPI: suspend () -> T): Flow<Resource<T>> = flow {
    try {
        emit(Resource.Loading<T>())
        val result = callAPI()
        emit(Resource.Success(result))
    } catch (e: HttpException) {
        emit(Resource.Error<T>(e.localizedMessage ?: "an unexpected error !"))
    } catch (e: IOException) {
        emit(Resource.Error<T>("Couldn't reach server. Check your internet connection !"))
    } catch (e: Exception) {
        emit(Resource.Error<T>("an unexpected error !"))
    }
}

fun mergeAnimalAndFlowerList(
    animalList: List<Animal>,
    flowerList: List<Flower>
): List<AnimalAndFlower> {
    val result: MutableList<AnimalAndFlower> = mutableListOf()
    for (animal in animalList) {
        val flower = flowerList.find { flower -> animal.id == flower.id }
        if (flower != null) result.add(AnimalAndFlower(animal, flower))
    }
    return result
}

fun commonLetters(str1: String, str2: String): String {
    var result = ""
    val commonLettersSet: MutableSet<Char> = mutableSetOf()
    for (ch1 in str1) {
        for (ch2 in str2) {
            if (ch1 == ch2) {
                commonLettersSet.add(ch1)
            }
        }
    }

    for (ch in commonLettersSet) {
        when (ch) {
            '[', ']', ',', ' ' -> Unit
            else -> result += ch
        }
    }
    return result
}

fun String.addComma(): String {
    var result = ""
    for (i in this.indices) {
        result += this[i]
        if (i < this.length - 1) {
            result += "،"
        }
    }
    return result
}

fun String.enNumberToFa(): String {
    var result = ""
    for (ch in this) {
        result += when (ch) {
            '0' -> '۰'
            '1' -> '۱'
            '2' -> '۲'
            '3' -> '۳'
            '4' -> '۴'
            '5' -> '۵'
            '6' -> '۶'
            '7' -> '۷'
            '8' -> '۸'
            '9' -> '۹'
            else -> Unit
        }
    }
    return result
}
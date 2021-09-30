package ir.erfan_mh_at.test_ba_salam.other

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import ir.erfan_mh_at.test_ba_salam.BaseApplication
import ir.erfan_mh_at.test_ba_salam.data.models.Animal
import ir.erfan_mh_at.test_ba_salam.data.models.Flower
import retrofit2.Response
import java.io.IOException

fun Application.checkInternetConnection(): Boolean {
    val connectivityManager = (this as BaseApplication).getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.activeNetworkInfo?.run {
            return when (type) {
                ConnectivityManager.TYPE_WIFI -> true
                ConnectivityManager.TYPE_MOBILE -> true
                ConnectivityManager.TYPE_ETHERNET -> true
                else -> false
            }
        }
    }
    return false
}


fun <E> handleRetrofitResponse(response: Response<E>): Resource<E> {
    if (response.isSuccessful) {
        response.body()?.let { body ->
            return Resource.Success(body)
        }
    }
    return Resource.Error(response.message(), response.body())
}

suspend fun <T> safeCallApi(
    app: Application,
    callApi: suspend () -> Response<T>
): Resource<T> {
    return try {
        if (app.checkInternetConnection()) {
            handleRetrofitResponse(callApi())
        } else {
            Resource.Error("No internet connection!")
        }
    } catch (t: Throwable) {
        when (t) {
            is IOException -> Resource.Error("Network Failure")
            else -> Resource.Error("Conversion Error")
        }
    }
}

fun mergeAnimalAndFlowerList(
    animalList: List<Animal>,
    flowerList: List<Flower>
): List<Pair<Animal, Flower>> {
    val result: MutableList<Pair<Animal, Flower>> = mutableListOf()
    for (animal in animalList) {
        val flower = flowerList.find { flower -> animal.id == flower.id }
        if (flower != null) result.add(Pair(animal, flower))
    }
    return result
}

fun numberOfCommonLetters(str1: String, str2: String): Int {
    val commonLetters: MutableSet<Char> = mutableSetOf()
    for (ch1 in str1) {
        for (ch2 in str2) {
            if (ch1 == ch2) {
                commonLetters.add(ch1)
            }
        }
    }
    return commonLetters.size
}
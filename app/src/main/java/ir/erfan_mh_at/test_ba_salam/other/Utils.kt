package ir.erfan_mh_at.test_ba_salam.other

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import ir.erfan_mh_at.test_ba_salam.BaseApplication
import retrofit2.Response
import java.io.IOException
import java.util.*
import kotlin.math.min

fun Application.checkInternetConnection(): Boolean {
    Log.d("SSSSSS", "checkInternetConnection")
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

fun <T> safeCallApi(
    app: Application,
    api: Response<T>
): Resource<T> {
    Log.d("SSSSSSS", "main")
    return try {
        if (app.checkInternetConnection()) {
            Log.d("SSSSSSS", "if")
            handleRetrofitResponse(api)
        } else {
            Log.d("SSSSSSS", "else")
            Resource.Error("No internet connection!")
        }
    } catch (t: Throwable) {
        Log.d("SSSSSSS", "catch")
        when (t) {
            is IOException -> Resource.Error("Network Failure")
            else -> Resource.Error("Conversion Error")
        }
    }
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
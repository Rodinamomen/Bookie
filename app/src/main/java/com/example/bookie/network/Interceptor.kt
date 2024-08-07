package com.example.bookie.network
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class Interceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var url = request.url.toString().replace("%26","$").replace("%20","+")
        val newRequest = Request.Builder().url(url).build()
        Log.d("NetworkRequest", "URL: $url") // Log the URL
        return chain.proceed(newRequest)
    }
}
package com.example.manu.firstapp


import com.loopj.android.http.*
/**
 * Created by manu on 05/09/17.
 */
class BabyoRestClient {
    private val BASE_URL = "http://babyo-2c2ff.appspot.com"

    private val client = AsyncHttpClient()

    fun get(url: String, params: RequestParams?, responseHandler: AsyncHttpResponseHandler) {
        client.addHeader("X-Requested-With","XMLHttpRequest")
        client.get(getAbsoluteUrl(url), params, responseHandler)
    }

    fun post(url: String, params: RequestParams?, responseHandler: AsyncHttpResponseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler)
    }

    private fun getAbsoluteUrl(relativeUrl: String): String {
        return BASE_URL + relativeUrl
    }

}
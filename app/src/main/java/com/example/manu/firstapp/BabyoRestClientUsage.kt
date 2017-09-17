package com.example.manu.firstapp

import android.content.Context
import org.json.*
import com.loopj.android.http.*
import cz.msebera.android.httpclient.Header
import android.content.Intent
import android.util.Log
import com.loopj.android.http.AsyncHttpResponseHandler




/**
 * Created by manu on 05/09/17.
 */
class BabyoRestClientUsage (val mContext: Context, val mAction : String) {

    companion object {
       val HTTP_RESPONSE: String = "http_response"
    }

    @Throws(JSONException::class)

    fun getPlayersName() {
        val brc = BabyoRestClient()

        brc.get("/getplayers", null, object : JsonHttpResponseHandler() {

            override fun onSuccess(statusCode: Int, headers: Array<Header>, players: JSONArray) {
                // récupérer tous les joueurs
                val l = players.length()
                var name = StringBuilder("")
                var index = 0
                while (index < l) {
                    val p = players.get(index)
                    if (p is JSONObject)
                    {
                        name.append( p.getString("nom"))
                        name.append("\n")
                    }
                    index++
                }
                val intent = Intent(mAction)
                intent.putExtra(HTTP_RESPONSE, name.toString())
                Log.d("RestCli", " NAme = $name")
                    // broadcast the completion
                mContext.sendBroadcast(intent)
                }



                // Do something with the response
                //println(tweetText)
        })
    }

    fun sendNotif( name :String) {
        val brc = BabyoRestClient()

        brc.get("/sendnotif/$name", null, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, resp:ByteArray ) {
                //val intent = Intent(mAction)
                // broadcast the completion
                //mContext.sendBroadcast(intent)
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })


    }

    fun removePlay() {
        val brc = BabyoRestClient()

        brc.get("/removeplayers", null, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, resp:ByteArray ) {
                //val intent = Intent(mAction)
                // broadcast the completion
                //mContext.sendBroadcast(intent)
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })


    }
}


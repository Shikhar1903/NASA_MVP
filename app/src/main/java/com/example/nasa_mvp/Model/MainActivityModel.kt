package com.example.nasa_mvp.Model


import android.util.Log
import android.util.Log.d
import com.example.nasa_mvp.Contract.ContractInterface
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivityModel: ContractInterface.Model{

    lateinit var date:String
    var listener:onFinishedListener?=null
    override fun getListOfDates(date1:String): String {

        Log.d("at model check",""+date1)
        date=date1
        var checkImageURL=""
        val retrofit:Retrofit= Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/planetary/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api:ApiService=retrofit.create(ApiService::class.java)
        api.fetchPictureData("DEMO_KEY",date).enqueue(object: retrofit2.Callback<Items> {
            override fun onFailure(call: Call<Items>, t: Throwable) {
            }
            override fun onResponse(call: Call<Items>, response: Response<Items>) {

               try{ checkImageURL= response.body()!!.url}
               catch (e:Exception){checkImageURL="https://via.placeholder.com/150x150.jpg?text=No+Image+Found"}
                if(checkImageURL.indexOf(".jpg")==-1)
                    checkImageURL="https://via.placeholder.com/150x150.jpg?text=No+Image+Found"
                listener?.finishedLoading(checkImageURL)
                d("check url in model",""+checkImageURL)
            }
        })
        //Why does NASA upload a video in Picture Of The Day?!Weird.
        return checkImageURL 
    }

    interface onFinishedListener{

        fun finishedLoading(imageUrl:String)
    }
    override fun setOnFinishedListener(listener: onFinishedListener?){
        this.listener=listener
    }
}

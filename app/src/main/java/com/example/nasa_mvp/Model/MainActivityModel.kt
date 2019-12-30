package com.example.nasa_mvp.Model


import android.content.Context
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
    var db:AppDatabase?=null
    var roomDao:roomItemsDao?=null

    override fun getListOfDates(date1:String,context:Context): String {

        Log.d("at model check",""+date1)
        date=date1
        var checkImageURL=""
        val retrofit:Retrofit= Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/planetary/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api:ApiService=retrofit.create(ApiService::class.java)

        db= AppDatabase.getAppDataBase(context)
        roomDao=db?.roomItemsDAO()


        var roomItemsEntity3=roomDao?.getUrlInfo(date)
        if(roomItemsEntity3?.room_url!=null) {
            listener?.finishedLoading(roomItemsEntity3!!.room_url)
            return roomItemsEntity3.room_url
        }
        api.fetchPictureData("DEMO_KEY",date).enqueue(object: retrofit2.Callback<Items> {
            override fun onFailure(call: Call<Items>, t: Throwable) {
            }
            override fun onResponse(call: Call<Items>, response: Response<Items>) {

                checkImageURL = try{
                    response.body()!!.url
                } catch (e:Exception){
                    "https://via.placeholder.com/150x150.jpg?text=No+Image+Found"
                }
                if(checkImageURL.indexOf(".jpg")==-1)
                    checkImageURL="https://via.placeholder.com/150x150.jpg?text=No+Image+Found"

                if (checkImageURL!="https://via.placeholder.com/150x150.jpg?text=No+Image+Found") {
                    var roomItemsEntity1 = roomItemsEntity(
                        room_date = response.body()!!.date,
                        room_explanation = response.body()!!.explanation,
                        room_title = response.body()!!.title,
                        room_url = checkImageURL
                    )

                    d("","What I want url-"+checkImageURL)
                    roomDao?.setUrlInfo(roomItemsEntity1)
                    var roomItemsEntity2 = roomDao?.getUrlInfo(date)
                    d("check url in model",""+roomItemsEntity2!!.room_url)
                    listener?.finishedLoading(roomItemsEntity2!!.room_url)
                }
                listener?.finishedLoading(checkImageURL)

            }
        })
        //Why does NASA upload a video in Picture Of The Day?!Weird
        return checkImageURL
    }

    interface onFinishedListener{

        fun finishedLoading(imageUrl:String)
    }
    override fun setOnFinishedListener(listener: onFinishedListener?){
        this.listener=listener
    }
}

package com.example.nasa_mvp.Contract

import android.content.Context
import com.example.nasa_mvp.Model.MainActivityModel

interface ContractInterface {

    interface  View{


        fun updateViewData(url:String)
    }

    interface Presenter{

        fun getImageUrl(date:String,context: Context):String
        fun iWasClicked(date:String,context: Context)

    }

    interface  Model{

        fun getListOfDates(date:String,context: Context):String
        fun setOnFinishedListener(listener: MainActivityModel.onFinishedListener?)


    }
}
package com.example.nasa_mvp.Contract

import com.example.nasa_mvp.Model.MainActivityModel

interface ContractInterface {

    interface  View{


        fun updateViewData(url:String)
    }

    interface Presenter{

        fun getImageUrl(date:String):String
        fun iWasClicked(url:String)

    }

    interface  Model{

        fun getListOfDates(date:String):String
        fun setOnFinishedListener(listener: MainActivityModel.onFinishedListener?)


    }
}
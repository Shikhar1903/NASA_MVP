package com.example.nasa_mvp.Presenter

import android.content.Context
import android.util.Log.d
import com.example.nasa_mvp.Contract.ContractInterface
import com.example.nasa_mvp.Model.MainActivityModel

class MainActivityPresenter(_view:ContractInterface.View):ContractInterface.Presenter,MainActivityModel.onFinishedListener {

    var view:ContractInterface.View=_view
    var model:ContractInterface.Model=MainActivityModel()


    override fun iWasClicked(date:String,context: Context) {

        getImageUrl(date,context)
        model.setOnFinishedListener(this)
    }

    override fun finishedLoading(imageUrl: String) {

        view.updateViewData(imageUrl)
    }

    override fun getImageUrl(date:String,context: Context): String {

        var url_temp=model.getListOfDates(date,context)
        d("check at presenter",""+url_temp)
        return url_temp
    }

}
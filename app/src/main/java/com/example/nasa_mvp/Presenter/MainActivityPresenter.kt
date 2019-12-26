package com.example.nasa_mvp.Presenter

import android.util.Log.d
import com.example.nasa_mvp.Contract.ContractInterface
import com.example.nasa_mvp.Model.MainActivityModel

class MainActivityPresenter(_view:ContractInterface.View):ContractInterface.Presenter,MainActivityModel.onFinishedListener {

    var view:ContractInterface.View=_view
    var model:ContractInterface.Model=MainActivityModel()


    override fun iWasClicked(date:String) {

        getImageUrl(date)
        model.setOnFinishedListener(this)
    }

    override fun finishedLoading(imageUrl: String) {

        view.updateViewData(imageUrl)
    }

    override fun getImageUrl(date:String): String {

        d("check at presenter",""+model.getListOfDates(date))
        if(model.getListOfDates(date) == "bruh")
            model.getListOfDates(date)

        return model.getListOfDates(date)
    }

}
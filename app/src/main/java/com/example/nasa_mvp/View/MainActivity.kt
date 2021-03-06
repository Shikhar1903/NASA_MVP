package com.example.nasa_mvp.View

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.nasa_mvp.Contract.ContractInterface
import com.example.nasa_mvp.Presenter.MainActivityPresenter
import com.example.nasa_mvp.R
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(),ContractInterface.View {

    lateinit var presenter: MainActivityPresenter
    lateinit var button: Button
    lateinit var mDateSetListener: DatePickerDialog.OnDateSetListener
    var date: String = getCalculatedDate("yyyy-MM-dd",0)
    private lateinit var spinner: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainActivityPresenter(this@MainActivity)
        button = findViewById(R.id.button)
        spinner = findViewById(R.id.progressBar)
        progressBar.visibility = View.GONE

        button.setOnClickListener {

            progressBar.visibility = View.VISIBLE
            presenter.iWasClicked(date,this)
        }

        textView.setOnClickListener {

            var cal: Calendar = Calendar.getInstance()
            var year: Int = cal.get(Calendar.YEAR)
            var month: Int = cal.get(Calendar.MONTH)
            var day: Int = cal.get(Calendar.DAY_OF_MONTH)
            var dialog = DatePickerDialog(
                this@MainActivity, android.R.style.Theme_Material_Dialog_NoActionBar_MinWidth,
                mDateSetListener, year, month, day
            )
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }
        mDateSetListener =
            DatePickerDialog.OnDateSetListener { datePicker: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                d("date-", "$year $month $dayOfMonth")
                val month1 = month + 1
                date = "$year-$month1-$dayOfMonth"
                textView.text = date

            }
    }

    override fun updateViewData(url: String) {

        d("url view after callback", "" + url)
        var APOD: ImageView = findViewById(R.id.imageView)

        Glide.with(this)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }

                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    return false
                }
            })
            .into(APOD)
    }

    fun getCalculatedDate(dateFormat: String, days: Int): String {
        val cal = Calendar.getInstance()
        val s = SimpleDateFormat(dateFormat)
        cal.add(Calendar.DAY_OF_YEAR, days)
        return s.format(Date(cal.timeInMillis))
    }
}

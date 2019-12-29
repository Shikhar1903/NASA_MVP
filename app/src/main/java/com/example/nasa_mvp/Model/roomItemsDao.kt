package com.example.nasa_mvp.Model

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface roomItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setUrlInfo(roomItemEntity: roomItemsEntity)

    @Query("SELECT * FROM roomItemsEntity WHERE room_date== :date")
    fun getUrlInfo(date:String): List<roomItemsEntity>
}
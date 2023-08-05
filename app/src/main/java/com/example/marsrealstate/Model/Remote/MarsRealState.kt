package com.example.marsrealstate.Model.Remote

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


//por esta vez esta misma entidad servira para datos remotos y locales

@Entity(tableName = "mars_table")
data class MarsRealState (

    @SerializedName("price")
    val price: Long,
    @SerializedName("id")
    @PrimaryKey
    val id: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("img_src")
    val img_src: String



        )
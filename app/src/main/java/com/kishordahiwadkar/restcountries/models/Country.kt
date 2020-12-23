package com.kishordahiwadkar.restcountries.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "country")
class Country (

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    @SerializedName("name") val name : String,

    @ColumnInfo(name = "capital")
    @SerializedName("capital") val capital : String,

    @ColumnInfo(name = "population")
    @SerializedName("population") val population : Int,
)
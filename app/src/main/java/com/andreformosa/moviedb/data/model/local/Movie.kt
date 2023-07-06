package com.andreformosa.moviedb.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "year")
    val year: Int,
    @ColumnInfo(name = "poster")
    val poster: String,
    @ColumnInfo(name = "overview")
    val overview: String,

    // Initially null since this is only added when fetching extra details
    @ColumnInfo(name = "rating")
    val rating: Double? = null,

    // Used only for the list
    @ColumnInfo(name = "page")
    var page: Int,
)

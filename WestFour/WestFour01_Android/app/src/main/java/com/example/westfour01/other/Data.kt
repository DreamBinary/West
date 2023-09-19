package com.example.westfour01

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

data class User(var username : String, var password : String)

@Entity(tableName = "novel")
data class NovelEntity(
    @PrimaryKey @NotNull @ColumnInfo(name = "fictionId") var fictionId: String,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "cover") var cover: String,
    @ColumnInfo(name = "author") var author : String,
    @ColumnInfo(name = "fictionType") var fictionType : String,
    @ColumnInfo(name = "descs") var descs : String
)

data class Chapter(var title: String, var chapterId: String)

data class Collection(val username: String, val novelTitle: String)

data class History(val username: String, val time: String, val novelTitle: String, val novelChapter: String, val chapterId: String)

data class Music(val name: String, val url: String, val picurl: String, val artistsname: String)
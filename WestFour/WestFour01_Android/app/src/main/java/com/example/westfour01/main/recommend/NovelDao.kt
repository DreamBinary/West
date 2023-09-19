package com.example.westfour01.main.recommend

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.westfour01.NovelEntity

@Dao
interface NovelDao {


    @Query("SELECT * FROM novel")
    fun findAll() : List<NovelEntity>

    @Query("SELECT * FROM novel WHERE title = :title")
    fun findByTitle(title: String): NovelEntity

    @Query("SELECT fictionId FROM novel WHERE title = :title")
    fun findFictionIdByTitle(title: String): String

    @Query("SELECT title FROM novel")
    fun findAllTitle() : List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg novelEntity: NovelEntity)

}
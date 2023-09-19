package com.example.westfour01.main.recommend

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.westfour01.NovelEntity

@Database(entities = [NovelEntity::class], version = 1, exportSchema = false)
abstract class NovelDatabase : RoomDatabase(){

    //获取数据表操作实例
    abstract fun novelDao(): NovelDao

    //单例模式
    companion object {
        private const val DB_NAME = "novel_database"
        @Volatile
        private var INSTANCE: NovelDatabase? = null

//        @Synchronized
//        fun getDatabase(context: Context): NovelDatabase{
//            val tempInstance = INSTANCE
//            if(tempInstance != null) { return tempInstance }
//            synchronized(this){
//                val instance = Room.databaseBuilder(
//                    context.applicationContext, NovelDatabase::class.java, DB_NAME)
////                    .fallbackToDestructiveMigration()
////                    .addMigrations(MIGRATION_1_2)
//                    .build()
//                INSTANCE = instance
//                return instance
//            }
//        }


        fun getDatabase(context: Context): NovelDatabase = INSTANCE ?:
        synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NovelDatabase::class.java, DB_NAME
            )
                .build()

        //升级语句
//        private val MIGRATION_1_2 = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE novel ADD COLUMN age INTEGER NOT NULL DEFAULT 0")
//            }
//        }
    }
}

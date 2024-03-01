package com.krishworld.hiltexample.data.local

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.krishworld.hiltexample.data.model.Post

@Database(entities = [Post::class], version = AppDatabase.VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        internal const val VERSION = 1
        private const val DATABASE_NAME = "hilt_database.db"
        private const val TAG = "AppDatabase"

        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ) // Creating DB when on new version makes sense.
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            Log.i(TAG, "DB Created")
                        }

                        override fun onOpen(db: SupportSQLiteDatabase) {
                            Log.i(TAG, "DB Opened")
                        }
                    })
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    abstract fun postDao(): PostDao
}
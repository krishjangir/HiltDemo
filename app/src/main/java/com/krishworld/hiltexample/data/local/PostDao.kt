package com.krishworld.hiltexample.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.krishworld.hiltexample.data.model.Post

@Dao
interface PostDao {
    @Query("SELECT * FROM posts")
    fun getAllPost(): List<Post>

    @Query("SELECT * FROM posts WHERE userId IN (:userIds)")
    fun loadAllPostByIds(userIds: IntArray): List<Post>

    @Insert
    fun insertAllPost(vararg post: Post)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(post: Post)

    @Delete
    fun deletePost(post: Post)
}
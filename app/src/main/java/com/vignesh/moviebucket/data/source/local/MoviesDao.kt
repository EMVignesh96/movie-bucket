/*
 * Copyright 2020 V App Factory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vignesh.moviebucket.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vignesh.moviebucket.data.model.Movie

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies WHERE libraryItemType = 1")
    fun observePopularMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE libraryItemType = 2")
    fun observeTopRatedMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE libraryItemType = 3")
    fun observeUpcomingMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE isLiked = 1")
    fun observeLikedMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE isWatched = 1")
    fun observeWatchedMovies(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(movie: Movie)

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovie(id: String): Movie

    @Update
    suspend fun update(movie: Movie)

    @Query("UPDATE movies SET inBucket = 0 WHERE id = :movieId")
    suspend fun removeFromBucket(movieId: String)

    @Query("UPDATE movies SET inBucket = 1 WHERE id = :movieId")
    suspend fun addToBucket(movieId: String)

    @Query("UPDATE movies SET isLiked = 0 WHERE id = :movieId")
    suspend fun unlikeMovie(movieId: String)

    @Query("UPDATE movies SET isLiked = 1 WHERE id = :movieId")
    suspend fun likeMovie(movieId: String)

    @Query("UPDATE movies SET isWatched = 0 WHERE id = :movieId")
    suspend fun unwatchMovie(movieId: String)

    @Query("UPDATE movies SET isWatched = 1 WHERE id = :movieId")
    suspend fun markAsWatched(movieId: String)

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun observeMovies(movieId: String): LiveData<Movie?>

    @Query("SELECT * FROM movies WHERE inBucket = 1")
    fun observeBucketList(): LiveData<List<Movie>>
}
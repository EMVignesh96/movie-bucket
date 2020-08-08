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

package com.vignesh.moviebucket

import android.content.Context
import androidx.room.Room
import com.vignesh.moviebucket.data.source.DefaultMovieRepository
import com.vignesh.moviebucket.data.source.MovieRepository
import com.vignesh.moviebucket.data.source.local.LocalDataSource
import com.vignesh.moviebucket.data.source.local.MovieDatabase
import com.vignesh.moviebucket.data.source.local.MovieLocalDataSource
import com.vignesh.moviebucket.data.source.remote.MovieRemoteDataSource

object ServiceLocator {

    private var database: MovieDatabase? = null

    @Volatile
    private var movieRepository: MovieRepository? = null

    fun provideMovieRepository(context: Context): MovieRepository {
        synchronized(this) {
            return movieRepository ?: movieRepository
            ?: createMovieRepository(context.applicationContext)
        }
    }

    private fun createMovieRepository(context: Context): MovieRepository {
        val newRepo =
            DefaultMovieRepository(createMovieLocalDataSource(context), MovieRemoteDataSource)
        movieRepository = newRepo
        return newRepo
    }

    private fun createMovieLocalDataSource(context: Context): LocalDataSource {
        val database = database ?: createDatabase(context)
        return MovieLocalDataSource(database.moviesDao())
    }

    private fun createDatabase(context: Context): MovieDatabase {
        val result = Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java, "Movies.db"
        ).build()
        database = result
        return result
    }
}
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
import androidx.lifecycle.map
import com.vignesh.moviebucket.data.Result
import com.vignesh.moviebucket.data.model.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class MovieLocalDataSource(
    private val moviesDao: MoviesDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : LocalDataSource {
    override fun observePopularMovies(): LiveData<Result<List<Movie>>> {
        return moviesDao.observePopularMovies().map { Result.Success(it) }
    }

    override fun observeTopRatedMovies(): LiveData<Result<List<Movie>>> {
        return moviesDao.observeTopRatedMovies().map { Result.Success(it) }
    }

    override fun observeUpcomingMovies(): LiveData<Result<List<Movie>>> {
        return moviesDao.observeUpcomingMovies().map { Result.Success(it) }
    }

    override fun observeLikedMovies(): LiveData<Result<List<Movie>>> {
        return moviesDao.observeLikedMovies().map { Result.Success(it) }
    }

    override fun observeWatchedMovies(): LiveData<Result<List<Movie>>> {
        return moviesDao.observeWatchedMovies().map { Result.Success(it) }
    }
}
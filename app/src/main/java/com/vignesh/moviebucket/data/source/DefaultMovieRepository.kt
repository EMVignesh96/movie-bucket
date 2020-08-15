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

package com.vignesh.moviebucket.data.source

import android.content.Context
import com.vignesh.moviebucket.data.Result
import com.vignesh.moviebucket.data.model.Movie
import com.vignesh.moviebucket.data.source.local.LocalDataSource
import com.vignesh.moviebucket.data.source.remote.RemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DefaultMovieRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MovieRepository {

    override fun observePopularMovies() = localDataSource.observePopularMovies()

    override fun observeTopRatedMovies() = localDataSource.observeTopRatedMovies()

    override fun observeUpcomingMovies() = localDataSource.observeUpcomingMovies()

    override fun observeLikedMovies() = localDataSource.observeLikedMovies()

    override fun observeWatchedMovies() = localDataSource.observeWatchedMovies()

    override suspend fun search(query: String) = remoteDataSource.search(query)

    override suspend fun loadLibraries() {
        var result = remoteDataSource.getPopularMovies()
        if (result is Result.Success) localDataSource.insertMovies(result.data)

        result = remoteDataSource.getTopRatedMovies()
        if (result is Result.Success) localDataSource.insertMovies(result.data)

        result = remoteDataSource.getUpcomingMovies()
        if (result is Result.Success) localDataSource.insertMovies(result.data)
    }

    override suspend fun getMovieDetails(id: String): Result<Movie?> {
        val localResult = localDataSource.getMovie(id)
        if (localResult is Result.Success && localResult.data != null) {
            return localResult
        }

        val remoteResult = remoteDataSource.getMovieDetails(id)
        return if (remoteResult is Result.Success) {
            localDataSource.insertMovie(remoteResult.data)
            remoteResult
        } else {
            remoteResult
        }
    }

    override suspend fun removeFromBucket(movieId: String) =
        localDataSource.removeFromBucket(movieId)

    override suspend fun addToBucket(movieId: String) = localDataSource.addToBucket(movieId)

    override suspend fun unlikeMovie(movieId: String) = localDataSource.unlikeMovie(movieId)

    override suspend fun likeMovie(movieId: String) = localDataSource.likeMovie(movieId)

    override suspend fun unwatchMovie(movieId: String) = localDataSource.unwatchMovie(movieId)

    override suspend fun markAsWatched(movieId: String) = localDataSource.markAsWatched(movieId)

    override fun observeMovie(id: String) = localDataSource.observeMovie(id)

    override fun observeBucketList() = localDataSource.observeBucketList()

    override suspend fun noMovies() = localDataSource.isMoviesTableEmpty()

    override suspend fun loadLocalLibrary(context: Context) =
        localDataSource.loadLocalLibrary(context)
}
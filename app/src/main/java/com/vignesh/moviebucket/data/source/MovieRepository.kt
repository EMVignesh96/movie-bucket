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

import androidx.lifecycle.LiveData
import com.vignesh.moviebucket.data.Result
import com.vignesh.moviebucket.data.model.Movie
import com.vignesh.moviebucket.data.model.SearchResult

interface MovieRepository {

    fun observePopularMovies(): LiveData<Result<List<Movie>>>

    fun observeTopRatedMovies(): LiveData<Result<List<Movie>>>

    fun observeUpcomingMovies(): LiveData<Result<List<Movie>>>

    fun observeLikedMovies(): LiveData<Result<List<Movie>>>

    fun observeWatchedMovies(): LiveData<Result<List<Movie>>>

    suspend fun search(query: String): Result<List<SearchResult>>

    suspend fun loadLibraries()

    suspend fun getMovieDetails(id: String): Result<Movie?>
}
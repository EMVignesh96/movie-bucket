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

package com.vignesh.moviebucket.ui.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.switchMap
import com.vignesh.moviebucket.data.Result
import com.vignesh.moviebucket.data.model.Movie
import com.vignesh.moviebucket.data.source.MovieRepository

class LibraryViewModel(private val movieRepo: MovieRepository) : ViewModel() {

    val popularMovies =
        movieRepo.observePopularMovies().distinctUntilChanged().switchMap { filterMovies(it) }

    val topRatedMovies =
        movieRepo.observeTopRatedMovies().distinctUntilChanged().switchMap { filterMovies(it) }

    val upcomingMovies =
        movieRepo.observeUpcomingMovies().distinctUntilChanged().switchMap { filterMovies(it) }

    val likedMovies =
        movieRepo.observeLikedMovies().distinctUntilChanged().switchMap { filterMovies(it) }

    val watchedMovies =
        movieRepo.observeWatchedMovies().distinctUntilChanged().switchMap { filterMovies(it) }

    private fun filterMovies(movieResult: Result<List<Movie>>): LiveData<List<Movie>> {
        val result = MutableLiveData<List<Movie>>()

        if (movieResult is Result.Success) {
            result.value = movieResult.data
        } else {
            result.value = emptyList()
        }

        return result
    }

}
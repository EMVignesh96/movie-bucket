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

package com.vignesh.moviebucket.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.vignesh.moviebucket.data.Result
import com.vignesh.moviebucket.data.model.Movie
import com.vignesh.moviebucket.data.source.MovieRepository
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val movieRepo: MovieRepository) : ViewModel() {

    private var _movieId = MutableLiveData<String>()

    fun start(movieId: String) {
        if (_dataLoading.value == true || _movieId.value == movieId) return
        _dataLoading.value = true
        loadMovieDetails(movieId)
        _movieId.value = movieId
    }

    private fun loadMovieDetails(movieId: String) {
        viewModelScope.launch {
            val result = movieRepo.getMovieDetails(movieId)
            _dataLoading.value = false
            if ((result is Result.Success && result.data == null) || result is Result.Error) {
                _loadingFailed.value = true
            }
        }
    }

    fun removeFromBucket(movieId: String) {
        viewModelScope.launch {
            movieRepo.removeFromBucket(movieId)
        }
    }

    fun addToBucket(movieId: String) {
        viewModelScope.launch {
            movieRepo.addToBucket(movieId)
        }
    }

    fun unlikeMovie(movieId: String) {
        viewModelScope.launch {
            movieRepo.unlikeMovie(movieId)
        }
    }

    fun likeMovie(movieId: String) {
        viewModelScope.launch {
            movieRepo.likeMovie(movieId)
        }
    }

    fun unwatchMovie(movieId: String) {
        viewModelScope.launch {
            movieRepo.unwatchMovie(movieId)
        }
    }

    fun markAsWatched(movieId: String) {
        viewModelScope.launch {
            movieRepo.markAsWatched(movieId)
        }
    }

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    private val _loadingFailed = MutableLiveData<Boolean>(false)
    val loadingFailed: LiveData<Boolean>
        get() = _loadingFailed

    private val _movie = _movieId.switchMap { id ->
        movieRepo.observeMovie(id).map { result ->
            if (result is Result.Success) result.data
            else null
        }
    }
    val movie: LiveData<Movie?>
        get() = _movie
}
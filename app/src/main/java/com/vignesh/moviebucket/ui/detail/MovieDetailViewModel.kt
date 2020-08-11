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

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vignesh.moviebucket.R
import com.vignesh.moviebucket.data.Result
import com.vignesh.moviebucket.data.model.Movie
import com.vignesh.moviebucket.data.source.MovieRepository
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val movieRepo: MovieRepository) : ViewModel() {

    private var id = ""

    fun start(movieId: String) {
        if (_dataLoading.value == true || id == movieId) return
        _dataLoading.value = true
        id = movieId
        getMovieDetails(id)
    }

    private fun getMovieDetails(movieId: String) {
        viewModelScope.launch {
            val result = movieRepo.getMovieDetails(movieId)
            _dataLoading.value = false
            if (result is Result.Success && result.data != null) {
                _movie.value = result.data
            } else {
                _loadingFailed.value = true
            }
        }
    }

    fun getRuntimeDisplayString(context: Context, runtimeMinutes: Int): String {
        val hours = runtimeMinutes / 60
        val min = runtimeMinutes % 60
        val resource = context.resources
        return "$hours${resource.getString(R.string.abbr_hours)} $min${resource.getString(R.string.abbr_minutes)}"
    }

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    private val _loadingFailed = MutableLiveData<Boolean>(false)
    val loadingFailed: LiveData<Boolean>
        get() = _loadingFailed

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie
}
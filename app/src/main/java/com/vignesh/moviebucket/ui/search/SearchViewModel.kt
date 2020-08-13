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

package com.vignesh.moviebucket.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.vignesh.moviebucket.Event
import com.vignesh.moviebucket.data.Result
import com.vignesh.moviebucket.data.model.Movie
import com.vignesh.moviebucket.data.model.SearchResult
import com.vignesh.moviebucket.data.source.MovieRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val movieRepo: MovieRepository) : ViewModel() {

    val popularMovies =
        movieRepo.observePopularMovies().distinctUntilChanged().switchMap { transformToResults(it) }

    private fun transformToResults(movieResult: Result<List<Movie>>): MutableLiveData<List<SearchResult>> {
        val result = MutableLiveData<List<SearchResult>>()

        if (movieResult is Result.Success) {
            val list = mutableListOf<SearchResult>()
            movieResult.data.forEach { movie ->
                val parts = movie.releaseDate.split("-")
                val releaseYear = if (parts.size == 3) parts[0] else ""
                list.add(
                    SearchResult(
                        movie.id,
                        movie.title,
                        movie.popularity,
                        releaseYear,
                        movie.rating,
                        movie.posterPath
                    )
                )
            }
            result.value = list
        } else {
            result.value = emptyList()
        }

        return result
    }

    private val _searchResult = MutableLiveData<List<SearchResult>>()
    val searchResult = _searchResult.distinctUntilChanged()


    fun search(query: String) {
        if (query.isNotEmpty()) {
            _isQueryEmpty.value = false
            viewModelScope.launch {
                val result = movieRepo.search(query)
                if (result is Result.Success) {
                    _searchResult.value = result.data
                }
            }
        } else {
            _isQueryEmpty.value = true
        }
    }

    fun onMovieClicked(movieId: String) {
        _movieClicked.value = Event(movieId)
    }

    private val _movieClicked = MutableLiveData<Event<String>>()
    val movieClicked: LiveData<Event<String>>
        get() = _movieClicked

    private val _isQueryEmpty = MutableLiveData<Boolean>(true)
    val isQueryEmpty: LiveData<Boolean>
        get() = _isQueryEmpty

}
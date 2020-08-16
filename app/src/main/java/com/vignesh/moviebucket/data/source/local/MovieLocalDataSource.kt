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

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.vignesh.moviebucket.data.Result
import com.vignesh.moviebucket.data.model.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

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

    override suspend fun insertMovies(movies: List<Movie>) {
        movies.forEach { movie -> insertMovie(movie) }
    }

    override suspend fun insertMovie(movie: Movie) {
        with(movie) {
            try {
                moviesDao.insert(this)
            } catch (e: SQLiteConstraintException) {
                val movieData = moviesDao.getMovie(id)

                inBucket = movieData.inBucket
                isLiked = movieData.isLiked
                isWatched = movieData.isWatched
                libraryItemType = movieData.libraryItemType

                moviesDao.update(this)
            }
        }
    }

    override suspend fun getMovie(id: String): Result<Movie?> {
        return try {
            Result.Success(moviesDao.getMovie(id))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun removeFromBucket(movieId: String) = moviesDao.removeFromBucket(movieId)

    override suspend fun addToBucket(movieId: String) = moviesDao.addToBucket(movieId)

    override suspend fun unlikeMovie(movieId: String) = moviesDao.unlikeMovie(movieId)

    override suspend fun likeMovie(movieId: String) = moviesDao.likeMovie(movieId)

    override suspend fun unwatchMovie(movieId: String) = moviesDao.unwatchMovie(movieId)

    override suspend fun markAsWatched(movieId: String) = moviesDao.markAsWatched(movieId)

    override fun observeMovie(movieId: String): LiveData<Result<Movie?>> =
        moviesDao.observeMovies(movieId).map { movie -> Result.Success(movie) }

    override fun observeBucketList(): LiveData<Result<List<Movie>>> {
        return moviesDao.observeBucketList().map { Result.Success(it) }
    }

    override suspend fun isMoviesTableEmpty() =
        if (moviesDao.getMovieCount() > 0) Result.Success(false)
        else Result.Success(true)

    override suspend fun loadLocalLibrary(context: Context) {
        val popular = loadPopularMovies(context)
        insertMovies(popular)

        val topRated = loadTopRatedMovies(context)
        insertMovies(topRated)

        val upcoming = loadUpcomingMovies(context)
        insertMovies(upcoming)
    }

    private fun loadUpcomingMovies(context: Context): List<Movie> {
        val fileContents = context.assets.open("upcoming_movies.json").bufferedReader()
            .use { reader -> reader.readText() }
        return parseMovieContent(fileContents)
    }

    private fun loadTopRatedMovies(context: Context): List<Movie> {
        val fileContents = context.assets.open("top_rated_movies.json").bufferedReader()
            .use { reader -> reader.readText() }
        return parseMovieContent(fileContents)
    }

    private fun loadPopularMovies(context: Context): List<Movie> {
        val fileContents = context.assets.open("popular_movies.json").bufferedReader()
            .use { reader -> reader.readText() }
        return parseMovieContent(fileContents)
    }

    private fun parseMovieContent(content: String): List<Movie> {
        val list = mutableListOf<Movie>()
        try {
            val jsonArray = JSONArray(content)
            for (i in 0 until jsonArray.length()) {
                list.add(parseMovie(jsonArray.getJSONObject(i)))
            }
        } catch (e: JSONException) {
            return list
        }
        return list
    }

    @Throws(JSONException::class)
    private fun parseMovie(movieJson: JSONObject): Movie {
        with(movieJson) {
            return Movie(
                getString(ID),
                getString(TITLE),
                getString(RELEASE_DATE),
                getInt(RUNTIME),
                getString(POPULARITY).toDouble(),
                getString(RATING),
                getString(GENRE),
                getString(OVERVIEW),
                getString(CAST),
                getString(POSTER_URL),
                getBoolean(IN_BUCKET),
                getBoolean(IS_LIKED),
                getBoolean(IS_WATCHED),
                getInt(LIBRARY_TYPE)
            )
        }
    }

    companion object {
        private const val ID = "id"
        private const val TITLE = "title"
        private const val RELEASE_DATE = "release_date"
        private const val RUNTIME = "runtime_minutes"
        private const val POPULARITY = "popularity"
        private const val RATING = "rating"
        private const val GENRE = "genre"
        private const val OVERVIEW = "over_view"
        private const val CAST = "cast"
        private const val POSTER_URL = "poster_path"
        private const val IN_BUCKET = "in_bucket"
        private const val IS_LIKED = "is_liked"
        private const val IS_WATCHED = "is_watched"
        private const val LIBRARY_TYPE = "library_item_type"
    }
}
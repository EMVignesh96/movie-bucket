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

package com.vignesh.moviebucket.util

import com.vignesh.moviebucket.data.model.Movie
import org.json.JSONArray
import org.json.JSONObject

// Data converters. Used as a utility to create json files in assets
fun List<Movie>.toJSONArray(): JSONArray {
    val array = JSONArray()
    forEach { movie -> array.put(movie.toJSONObject()) }
    return array
}

fun Movie.toJSONObject() = JSONObject().apply {
    put(ID, id)
    put(TITLE, title)
    put(RELEASE_DATE, releaseDate)
    put(RUNTIME, runtimeMinutes)
    put(POPULARITY, popularity)
    put(RATING, rating)
    put(GENRE, genre)
    put(OVERVIEW, overView)
    put(CAST, cast)
    put(POSTER_URL, posterPath)
    put(IN_BUCKET, inBucket)
    put(IS_LIKED, isLiked)
    put(IS_WATCHED, isWatched)
    put(LIBRARY_TYPE, libraryItemType)
}

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
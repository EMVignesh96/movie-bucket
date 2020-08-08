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

package com.vignesh.moviebucket.data.model

data class Movie(
    val id: String,
    val title: String,
    val releaseDate: Long,
    val runtimeMinutes: Int,
    val rating: Float,
    val genre: String,
    val overView: String,
    val cast: String,
    val posterUrl: String,
    val inBucket: Boolean = false,
    val isLiked: Boolean = false,
    val isWatched: Boolean = false,
    val libraryType: Int = 0
)
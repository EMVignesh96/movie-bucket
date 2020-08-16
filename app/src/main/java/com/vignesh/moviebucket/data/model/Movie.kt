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

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey val id: String,
    val title: String,
    val releaseDate: String,
    val runtimeMinutes: Int,
    val popularity: Double,
    val rating: String,
    val genre: String,
    val overView: String,
    val cast: String,
    val posterPath: String,
    var inBucket: Boolean = false,
    var isLiked: Boolean = false,
    var isWatched: Boolean = false,
    var libraryItemType: Int = 0
)
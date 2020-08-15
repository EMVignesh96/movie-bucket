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

package com.vignesh.moviebucket.ui

import android.content.Context
import com.vignesh.moviebucket.R

fun getRuntimeDisplayString(context: Context, runtimeMinutes: Int): String {
    val hours = runtimeMinutes / 60
    val min = runtimeMinutes % 60
    val resource = context.resources
    return "$hours${resource.getString(R.string.abbr_hours)} $min${resource.getString(R.string.abbr_minutes)}"
}

fun getReleaseYear(releaseDate: String): String {
    val parts = releaseDate.split("-")
    return if (parts.size == 3) parts[0] else ""
}

fun getFirstGenre(genre: String): String {
    val parts = genre.split("|")
    return if (parts.isNotEmpty()) parts[0] else ""
}
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

package com.vignesh.moviebucket.data.source.remote

import com.vignesh.moviebucket.data.Result
import com.vignesh.moviebucket.data.model.SearchResult
import org.json.JSONObject

object MovieRemoteDataSource : RemoteDataSource {

    override suspend fun search(query: String): Result<List<SearchResult>> {
        return try {
            val response = JSONObject(TMDbApi.retrofitService.search(query))
            val results = response.getJSONArray("results")
            val list = mutableListOf<SearchResult>()

            for (i in 0 until results.length()) {
                list.add(parseSearchResult(results.getJSONObject(i)))
            }

            Result.Success(list)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private fun parseSearchResult(result: JSONObject): SearchResult {
        return with(result) {
            val releaseYear = with(getString("release_date")) {
                val parts = split("-")
                if (parts.size == 3) parts[0]
                else ""
            }
            SearchResult(
                optString("id"),
                getString("title"),
                optString("popularity"),
                releaseYear,
                getString("vote_average"),
                getString("poster_path")
            )
        }
    }

}
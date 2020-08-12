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

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val API_KEY = "<your-api-key-here>"

private val retrofit =
    Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(ScalarsConverterFactory.create())
        .build()

interface TMDbApiService {
    @GET("search/movie")
    suspend fun search(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = API_KEY
    ): String

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String = API_KEY): String

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey: String = API_KEY): String

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") apiKey: String = API_KEY): String

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: String,
        @Query("api_key") apiKey: String = API_KEY
    ): String

    @GET("movie/{id}/credits")
    suspend fun getMovieCredits(
        @Path("id") id: String,
        @Query("api_key") apiKey: String = API_KEY
    ): String
}

object TMDbApi {
    val retrofitService: TMDbApiService by lazy { retrofit.create(TMDbApiService::class.java) }
}
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

package com.vignesh.moviebucket.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.vignesh.moviebucket.MovieBucketApp

class DownloadLibrariesWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val movieRepo = (applicationContext as MovieBucketApp).movieRepository
        val noMovies = movieRepo.noMovies()
        if (noMovies is com.vignesh.moviebucket.data.Result.Success && noMovies.data) {
            movieRepo.loadLibraries()
        }
        return Result.success()
    }

    companion object {
        fun startWork(context: Context) {
            WorkManager.getInstance(context)
                .enqueue(OneTimeWorkRequest.from(DownloadLibrariesWorker::class.java))
        }
    }
}
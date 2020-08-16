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

package com.vignesh.moviebucket

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.vignesh.moviebucket.databinding.ActivitySplashBinding
import com.vignesh.moviebucket.worker.DownloadLibrariesWorker

class SplashActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        DownloadLibrariesWorker.startWork(baseContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySplashBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_splash)
        with(binding) {
            root.systemUiVisibility = android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            startActivity(Intent(this, MainActivity::class.java))
        }, 1000)
    }
}
/*
 * Copyright 2020 Vignesh
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

package com.vignesh.moviebucket.ui.shuffle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vignesh.moviebucket.R

class ShuffleFragment : Fragment() {

    private lateinit var shuffleViewModel: ShuffleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        shuffleViewModel =
            ViewModelProviders.of(this).get(ShuffleViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_shuffle, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        shuffleViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}
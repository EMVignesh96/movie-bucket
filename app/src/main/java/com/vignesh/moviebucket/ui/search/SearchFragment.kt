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

package com.vignesh.moviebucket.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.vignesh.moviebucket.R
import com.vignesh.moviebucket.databinding.FragmentSearchBinding
import com.vignesh.moviebucket.util.getViewModelFactory

private val TAG = SearchFragment::class.java.simpleName

class SearchFragment : Fragment() {

    private val viewModel by viewModels<SearchViewModel> { getViewModelFactory() }
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        viewModel.searchResult.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, it.toString())
        })

        binding.topView.setOnApplyWindowInsetsListener { view, windowInsets ->
            val topInset = windowInsets.systemWindowInsetTop
            if (view.layoutParams.height != topInset) {
                view.layoutParams.height = topInset
                view.requestLayout()
            }
            return@setOnApplyWindowInsetsListener windowInsets
        }

        return binding.root
    }
}
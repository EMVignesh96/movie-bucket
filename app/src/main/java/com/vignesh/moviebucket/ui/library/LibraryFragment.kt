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

package com.vignesh.moviebucket.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.vignesh.moviebucket.R
import com.vignesh.moviebucket.databinding.FragmentLibraryBinding
import com.vignesh.moviebucket.util.obtainViewModel

class LibraryFragment : Fragment() {

    private lateinit var binding: FragmentLibraryBinding
    private lateinit var viewModel: LibraryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_library, container, false)

        viewModel = obtainViewModel(LibraryViewModel::class.java)

        binding.topView.setOnApplyWindowInsetsListener { view, windowInsets ->
            val topInset = windowInsets.systemWindowInsetTop
            view.layoutParams.height = topInset
            view.requestLayout()
            return@setOnApplyWindowInsetsListener windowInsets
        }

        setUpRecyclerViews()

        observeData()

        return binding.root
    }

    private fun observeData() {

        with(viewModel) {
            popularMovies.observe(viewLifecycleOwner, Observer { popularMovies ->

            })

            topRatedMovies.observe(viewLifecycleOwner, Observer { topRatedMovies ->

            })

            upcomingMovies.observe(viewLifecycleOwner, Observer { upcomingMovies ->

            })

            likedMovies.observe(viewLifecycleOwner, Observer { likedMovies ->

            })

            watchedMovies.observe(viewLifecycleOwner, Observer { watchedMovies ->

            })
        }
    }

    private fun setUpRecyclerViews() {
        with(binding) {
            popularMoviesRecycler.apply {
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }

            topRatedMoviesRecycler.apply {
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }

            upcomingMoviesRecycler.apply {
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }

            likedMoviesRecycler.apply {
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }

            watchedMoviesRecycler.apply {
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }
        }
    }
}
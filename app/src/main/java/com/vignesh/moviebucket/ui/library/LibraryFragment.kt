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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vignesh.moviebucket.EventObserver
import com.vignesh.moviebucket.R
import com.vignesh.moviebucket.databinding.FragmentLibraryBinding
import com.vignesh.moviebucket.util.getViewModelFactory

private var TAG: String = LibraryFragment::class.java.simpleName

class LibraryFragment : Fragment() {

    private lateinit var binding: FragmentLibraryBinding
    private val viewModel by viewModels<LibraryViewModel> { getViewModelFactory() }

    private val popularMoviesAdapter = LibraryMovieAdapter()
    private val topRatedMoviesAdapter = LibraryMovieAdapter()
    private val upcomingMoviesAdapter = LibraryMovieAdapter()
    private val likedMoviesAdapter = LibraryMovieAdapter()
    private val watchedMoviesAdapter = LibraryMovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_library, container, false)

        binding.topView.setOnApplyWindowInsetsListener { view, windowInsets ->
            val topInset = windowInsets.systemWindowInsetTop
                    view.layoutParams.height = topInset
            view.requestLayout()
            return@setOnApplyWindowInsetsListener windowInsets
        }

        setUpRecyclerViews()
        setItemClickListener()

        observeData()

        return binding.root
    }

    private fun setItemClickListener() {

        val listener: (String) -> Unit = { movieId -> viewModel.onMovieClicked(movieId) }

        popularMoviesAdapter.setOnItemClickListener(listener)
        topRatedMoviesAdapter.setOnItemClickListener(listener)
        upcomingMoviesAdapter.setOnItemClickListener(listener)
        likedMoviesAdapter.setOnItemClickListener(listener)
        watchedMoviesAdapter.setOnItemClickListener(listener)

        viewModel.movieClicked.observe(
            viewLifecycleOwner,
            EventObserver { movieId -> onItemClicked(movieId) })
    }

    private fun onItemClicked(movieId: String) {
        val action = LibraryFragmentDirections.actionNavigationLibraryToMovieDetailFragment(movieId)
        findNavController().navigate(action)
    }

    private fun observeData() {

        with(viewModel) {
            popularMovies.observe(viewLifecycleOwner, Observer { popularMovies ->
                if (popularMovies.isNotEmpty()) {
                    binding.popularLayout.visibility = View.VISIBLE
                    popularMoviesAdapter.setData(popularMovies)
                }
            })

            topRatedMovies.observe(viewLifecycleOwner, Observer { topRatedMovies ->
                if (topRatedMovies.isNotEmpty()) {
                    binding.topRatedLayout.visibility = View.VISIBLE
                    topRatedMoviesAdapter.setData(topRatedMovies)
                }
            })

            upcomingMovies.observe(viewLifecycleOwner, Observer { upcomingMovies ->
                if (upcomingMovies.isNotEmpty()) {
                    binding.upcomingLayout.visibility = View.VISIBLE
                    upcomingMoviesAdapter.setData(upcomingMovies)
                }
            })

            likedMovies.observe(viewLifecycleOwner, Observer { likedMovies ->
                if (likedMovies.isNotEmpty()) {
                    binding.likedLayout.visibility = View.VISIBLE
                    likedMoviesAdapter.setData(likedMovies)
                }
            })

            watchedMovies.observe(viewLifecycleOwner, Observer { watchedMovies ->
                if (watchedMovies.isNotEmpty()) {
                    binding.watchedLayout.visibility = View.VISIBLE
                    watchedMoviesAdapter.setData(watchedMovies)
                }
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
                adapter = popularMoviesAdapter
            }

            topRatedMoviesRecycler.apply {
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                adapter = topRatedMoviesAdapter
            }

            upcomingMoviesRecycler.apply {
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                adapter = upcomingMoviesAdapter
            }

            likedMoviesRecycler.apply {
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                adapter = likedMoviesAdapter
            }

            watchedMoviesRecycler.apply {
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                adapter = watchedMoviesAdapter
            }
        }
    }
}
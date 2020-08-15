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
import androidx.recyclerview.widget.GridLayoutManager
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
            val itemsPerRow = requireContext().resources.getInteger(R.integer.library_items_per_row)
            popularMovies.observe(viewLifecycleOwner, Observer { popularMovies ->
                if (popularMovies.isNotEmpty()) {
                    binding.popularLayout.visibility = View.VISIBLE
                    popularMoviesAdapter.setData(popularMovies)
                    if (popularMovies.size >= 2 * itemsPerRow) {
                        binding.popularMoviesRecycler.layoutManager =
                            GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
                    }
                }
            })

            topRatedMovies.observe(viewLifecycleOwner, Observer { topRatedMovies ->
                if (topRatedMovies.isNotEmpty()) {
                    binding.topRatedLayout.visibility = View.VISIBLE
                    topRatedMoviesAdapter.setData(topRatedMovies)
                    if (topRatedMovies.size >= 2 * itemsPerRow) {
                        binding.topRatedMoviesRecycler.layoutManager =
                            GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
                    }
                }
            })

            upcomingMovies.observe(viewLifecycleOwner, Observer { upcomingMovies ->
                if (upcomingMovies.isNotEmpty()) {
                    binding.upcomingLayout.visibility = View.VISIBLE
                    upcomingMoviesAdapter.setData(upcomingMovies)
                    if (upcomingMovies.size >= 2 * itemsPerRow) {
                        binding.upcomingMoviesRecycler.layoutManager =
                            GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
                    }
                }
            })

            likedMovies.observe(viewLifecycleOwner, Observer { likedMovies ->
                if (likedMovies.isNotEmpty()) {
                    binding.likedLayout.visibility = View.VISIBLE
                    likedMoviesAdapter.setData(likedMovies)
                    if (likedMovies.size >= 2 * itemsPerRow) {
                        binding.likedMoviesRecycler.layoutManager =
                            GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
                    }
                }
            })

            watchedMovies.observe(viewLifecycleOwner, Observer { watchedMovies ->
                if (watchedMovies.isNotEmpty()) {
                    binding.watchedLayout.visibility = View.VISIBLE
                    watchedMoviesAdapter.setData(watchedMovies)
                    if (watchedMovies.size >= 2 * itemsPerRow) {
                        binding.watchedMoviesRecycler.layoutManager =
                            GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
                    }
                }
            })
        }
    }

    private fun setUpRecyclerViews() {
        with(binding) {
            popularMoviesRecycler.apply {
                layoutManager = GridLayoutManager(
                    context,
                    1,
                    GridLayoutManager.HORIZONTAL,
                    false
                )
                adapter = popularMoviesAdapter
            }

            topRatedMoviesRecycler.apply {
                layoutManager = GridLayoutManager(
                    context,
                    1,
                    GridLayoutManager.HORIZONTAL,
                    false
                )
                adapter = topRatedMoviesAdapter
            }

            upcomingMoviesRecycler.apply {
                layoutManager = GridLayoutManager(
                    context,
                    1,
                    GridLayoutManager.HORIZONTAL,
                    false
                )
                adapter = upcomingMoviesAdapter
            }

            likedMoviesRecycler.apply {
                layoutManager = GridLayoutManager(
                    context,
                    1,
                    GridLayoutManager.HORIZONTAL,
                    false
                )
                adapter = likedMoviesAdapter
            }

            watchedMoviesRecycler.apply {
                layoutManager = GridLayoutManager(
                    context,
                    1,
                    GridLayoutManager.HORIZONTAL,
                    false
                )
                adapter = watchedMoviesAdapter
            }
        }
    }
}
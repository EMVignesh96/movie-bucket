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

package com.vignesh.moviebucket.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.vignesh.moviebucket.R
import com.vignesh.moviebucket.data.model.Movie
import com.vignesh.moviebucket.databinding.FragmentDetailBinding
import com.vignesh.moviebucket.util.getViewModelFactory

private var TAG = MovieDetailFragment::class.java.simpleName

class MovieDetailFragment : Fragment() {

    private val viewModel by viewModels<MovieDetailViewModel> { getViewModelFactory() }
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        val args: MovieDetailFragmentArgs by navArgs()

        binding.topView.setOnApplyWindowInsetsListener { view, windowInsets ->
            val topInset = windowInsets.systemWindowInsetTop
            view.layoutParams.height = topInset
            view.requestLayout()
            return@setOnApplyWindowInsetsListener windowInsets
        }

        viewModel.start(args.movieId)

        viewModel.dataLoading.observe(viewLifecycleOwner, Observer { dataLoading ->
            binding.loadingIndicator.visibility = if (dataLoading) View.VISIBLE else View.INVISIBLE
        })

        viewModel.loadingFailed.observe(viewLifecycleOwner, Observer { loadingFailed ->
            binding.errorContainer.visibility = if (loadingFailed) View.VISIBLE else View.INVISIBLE
        })

        viewModel.movie.observe(viewLifecycleOwner, Observer { movie ->
            if (movie == null) return@Observer
            setMovieData(movie)
        })

        return binding.root
    }

    private fun setMovieData(movie: Movie) {
        with(binding) {
            binding.movie = movie
            runtime.text =
                viewModel.getRuntimeDisplayString(requireContext(), movie.runtimeMinutes)

            bucketToggle.setCompoundDrawablesWithIntrinsicBounds(
                null,
                ContextCompat.getDrawable(
                    requireContext(),
                    if (movie.inBucket) R.drawable.ic_baseline_done_24 else R.drawable.ic_baseline_add_24
                ),
                null,
                null
            )
            bucketToggle.setOnClickListener {
                if (movie.inBucket) viewModel.removeFromBucket(movie.id)
                else viewModel.addToBucket(movie.id)
            }

            binding.likeToggle.text =
                if (movie.isLiked) requireContext().getString(R.string.action_liked) else requireContext().getString(
                    R.string.action_like
                )
            likeToggle.setCompoundDrawablesWithIntrinsicBounds(
                null,
                ContextCompat.getDrawable(
                    requireContext(),
                    if (movie.isLiked) R.drawable.ic_baseline_thumb_up_24 else R.drawable.ic_outline_thumb_up_24
                ),
                null,
                null
            )
            likeToggle.setOnClickListener {
                if (movie.isLiked) viewModel.unlikeMovie(movie.id)
                else viewModel.likeMovie(movie.id)
            }

            binding.watchToggle.text =
                if (movie.isWatched) requireContext().getString(R.string.action_watched) else requireContext().getString(
                    R.string.action_watch
                )
            watchToggle.setCompoundDrawablesWithIntrinsicBounds(
                null,
                requireContext().getDrawable(if (movie.isWatched) R.drawable.ic_baseline_movie_24 else R.drawable.ic_outline_movie_24),
                null,
                null
            )
            watchToggle.setOnClickListener {
                if (movie.isWatched) viewModel.unwatchMovie(movie.id)
                else viewModel.markAsWatched(movie.id)
            }

            detailsContainer.visibility = View.VISIBLE
        }
    }
}
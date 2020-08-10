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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vignesh.moviebucket.R
import com.vignesh.moviebucket.data.model.Movie
import com.vignesh.moviebucket.databinding.ItemLibraryBinding

class LibraryMovieAdapter : RecyclerView.Adapter<LibraryMovieAdapter.LibraryItemHolder>() {

    private val movies = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryItemHolder {
        val binding: ItemLibraryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_library,
            parent,
            false
        )
        return LibraryItemHolder(binding)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: LibraryItemHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun setData(movies: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    inner class LibraryItemHolder(private val binding: ItemLibraryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            with(binding) {
                description = movie.title
                imagePath = movie.posterUrl
                executePendingBindings()
            }
        }

    }
}
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

package com.vignesh.moviebucket.ui.bucketlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vignesh.moviebucket.R
import com.vignesh.moviebucket.data.model.Movie
import com.vignesh.moviebucket.databinding.ItemBucketBinding
import com.vignesh.moviebucket.databinding.ItemDummyBinding
import com.vignesh.moviebucket.databinding.ItemNoListBinding
import com.vignesh.moviebucket.databinding.ItemTitleBinding
import com.vignesh.moviebucket.ui.getFirstGenre
import com.vignesh.moviebucket.ui.getReleaseYear
import com.vignesh.moviebucket.ui.getRuntimeDisplayString

private var TAG = BucketListAdapter::class.java.simpleName

class BucketListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_DUMMY = 0
        private const val VIEW_TYPE_TITLE = 1
        private const val VIEW_TYPE_BUCKET_ITEM = 2
        private const val VIEW_TYPE_NO_ITEMS = 3
    }

    private var onItemClicked: ((String) -> Unit)? = null
    private val movies = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_DUMMY -> DummyViewHolder(
                DataBindingUtil.inflate(
                    inflater,
                    R.layout.item_dummy,
                    parent,
                    false
                )
            )
            VIEW_TYPE_TITLE -> TitleViewHolder(
                DataBindingUtil.inflate(
                    inflater,
                    R.layout.item_title,
                    parent,
                    false
                )
            )
            VIEW_TYPE_NO_ITEMS -> NoListViewHolder(
                DataBindingUtil.inflate(
                    inflater,
                    R.layout.item_no_list,
                    parent,
                    false
                )
            )
            else -> BucketItemViewHolder(
                DataBindingUtil.inflate(
                    inflater,
                    R.layout.item_bucket,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount() = movies.size + 2

    override fun getItemViewType(position: Int): Int {
        return if (movies.isEmpty()) {
            if (position == 0) VIEW_TYPE_DUMMY
            else VIEW_TYPE_NO_ITEMS
        } else {
            when (position) {
                0 -> VIEW_TYPE_DUMMY
                1 -> VIEW_TYPE_TITLE
                else -> VIEW_TYPE_BUCKET_ITEM
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_DUMMY -> (holder as DummyViewHolder).bind()
            VIEW_TYPE_TITLE -> (holder as TitleViewHolder)
            VIEW_TYPE_NO_ITEMS -> (holder as NoListViewHolder)
            else -> (holder as BucketItemViewHolder).bind(movies[position - 2])
        }
    }

    fun setData(list: List<Movie>) {
        with(movies) {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(l: ((String) -> Unit)?) {
        onItemClicked = l
    }

    class DummyViewHolder(private val binding: ItemDummyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.topView.setOnApplyWindowInsetsListener { view, windowInsets ->
                val topInset = windowInsets.systemWindowInsetTop
                if (view.layoutParams.height != topInset) {
                    view.layoutParams.height = topInset
                }
                return@setOnApplyWindowInsetsListener windowInsets
            }
        }
    }

    class TitleViewHolder(binding: ItemTitleBinding) : RecyclerView.ViewHolder(binding.root)

    class NoListViewHolder(binding: ItemNoListBinding) : RecyclerView.ViewHolder(binding.root)

    inner class BucketItemViewHolder(private val binding: ItemBucketBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            with(binding) {
                binding.movie = movie
                releaseYear = getReleaseYear(movie.releaseDate)
                runtime = getRuntimeDisplayString(binding.root.context, movie.runtimeMinutes)
                genre = getFirstGenre(movie.genre)
                root.setOnClickListener {
                    onItemClicked?.let { onItemClicked -> onItemClicked(movies[adapterPosition - 2].id) }
                }
                executePendingBindings()
            }
        }
    }

}
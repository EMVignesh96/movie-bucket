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

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vignesh.moviebucket.R
import com.vignesh.moviebucket.data.model.SearchResult
import com.vignesh.moviebucket.databinding.ItemNoSearchResultsBinding
import com.vignesh.moviebucket.databinding.ItemSearchResultBinding
import com.vignesh.moviebucket.databinding.ItemSearchResultsTitleBinding

class SearchResultAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_TITLE: Int = 0
        private const val VIEW_TYPE_RESULT: Int = 1
        private const val VIEW_TYPE_NO_RESULTS = 2
    }

    private val searchResults = mutableListOf<SearchResult>()
    var isSearchResult = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_NO_RESULTS -> {
                val binding: ItemNoSearchResultsBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_no_search_results,
                    parent,
                    false
                )
                NoResultsViewHolder(binding)
            }

            VIEW_TYPE_TITLE -> {
                val binding: ItemSearchResultsTitleBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_search_results_title,
                    parent,
                    false
                )
                TitleViewHolder(binding)
            }
            else -> {
                val binding: ItemSearchResultBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_search_result,
                    parent,
                    false
                )
                SearchResultViewHolder(binding)
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            searchResults.isEmpty() -> VIEW_TYPE_NO_RESULTS
            position == 0 -> VIEW_TYPE_TITLE
            else -> VIEW_TYPE_RESULT
        }
    }

    override fun getItemCount() = searchResults.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when {
            getItemViewType(position) == VIEW_TYPE_TITLE -> {
                (holder as TitleViewHolder).bind(if (isSearchResult) R.string.title_search_result else R.string.title_popular)
            }
            getItemViewType(position) == VIEW_TYPE_RESULT -> {
                (holder as SearchResultViewHolder).bind(searchResults[position - 1])
            }
            else -> {
                (holder as NoResultsViewHolder).bind()
            }
        }
    }

    fun setData(searchResults: List<SearchResult>) {
        this.searchResults.apply {
            clear()
            addAll(searchResults)
        }
        notifyDataSetChanged()
    }

    inner class TitleViewHolder(private val binding: ItemSearchResultsTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(stringResId: Int) {
            with(binding) {
                title = context.resources.getString(stringResId)
                executePendingBindings()
            }
        }
    }

    class SearchResultViewHolder(private val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: SearchResult) {
            with(binding) {
                this.result = result
                executePendingBindings()
            }
        }
    }

    class NoResultsViewHolder(private val binding: ItemNoSearchResultsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.executePendingBindings()
        }
    }
}
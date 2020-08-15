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
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vignesh.moviebucket.EventObserver
import com.vignesh.moviebucket.R
import com.vignesh.moviebucket.databinding.FragmentSearchBinding
import com.vignesh.moviebucket.util.getViewModelFactory

private val TAG = SearchFragment::class.java.simpleName

class SearchFragment : Fragment() {

    private val viewModel by viewModels<SearchViewModel> { getViewModelFactory() }
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchResultAdapter: SearchResultAdapter
    private lateinit var popularAdapter: SearchResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        searchResultAdapter = SearchResultAdapter(requireContext())
        searchResultAdapter.setOnItemSelectedListener { id -> viewModel.onMovieClicked(id) }

        popularAdapter = SearchResultAdapter(requireContext())
        popularAdapter.setOnItemSelectedListener { id -> viewModel.onMovieClicked(id) }

        viewModel.searchResult.observe(viewLifecycleOwner, Observer { searchResults ->
            searchResultAdapter.setData(searchResults)
            swapAdapter(searchResultAdapter)
        })

        viewModel.movieClicked.observe(viewLifecycleOwner, EventObserver { movieId ->
            val action =
                SearchFragmentDirections.actionNavigationSearchToMovieDetailFragment(movieId)
            findNavController().navigate(action)
        })

        viewModel.isQueryEmpty.observe(
            viewLifecycleOwner,
            Observer { isQueryEmpty -> if (isQueryEmpty) swapAdapter(popularAdapter) })

        viewModel.popularMovies.observe(viewLifecycleOwner, Observer { movies ->
            popularAdapter.isSearchResult = false
            popularAdapter.setData(movies)
        })

        binding.topView.setOnApplyWindowInsetsListener { view, windowInsets ->
            val topInset = windowInsets.systemWindowInsetTop
            if (view.layoutParams.height != topInset) {
                view.layoutParams.height = topInset
                view.requestLayout()
            }
            return@setOnApplyWindowInsetsListener windowInsets
        }

        binding.searchQuery.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(searchText: Editable?) {
                searchText?.let { query ->
                    viewModel.search(query.toString())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })

        setUpRecyclerView(binding.searchResultRecycler)

        return binding.root
    }

    private fun swapAdapter(adapter: SearchResultAdapter) {
        binding.searchResultRecycler.adapter = adapter
    }

    private fun setUpRecyclerView(searchResultRecycler: RecyclerView) {
        searchResultRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = searchResultAdapter
        }
    }
}
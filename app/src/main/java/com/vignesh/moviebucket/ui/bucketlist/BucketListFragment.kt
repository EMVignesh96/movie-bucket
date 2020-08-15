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
import com.vignesh.moviebucket.R
import com.vignesh.moviebucket.databinding.FragmentListBinding
import com.vignesh.moviebucket.util.getViewModelFactory

private var TAG = BucketListFragment::class.java.simpleName

class BucketListFragment : Fragment() {

    private val viewModel by viewModels<BucketListViewModel> { getViewModelFactory() }
    private lateinit var binding: FragmentListBinding
    private val bucketListAdapter = BucketListAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)

        setUpRecyclerView()

        viewModel.bucketList.observe(viewLifecycleOwner, Observer { bucketList ->
            bucketListAdapter.setData(bucketList)
        })

        return binding.root
    }

    private fun setUpRecyclerView() {
        bucketListAdapter.setOnItemClickListener { movieId ->
            val action =
                BucketListFragmentDirections.actionNavigationListToMovieDetailFragment(movieId)
            findNavController().navigate(action)
        }
        binding.bucketListRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = bucketListAdapter
        }
    }
}
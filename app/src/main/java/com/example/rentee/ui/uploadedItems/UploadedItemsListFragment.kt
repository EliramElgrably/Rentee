package com.example.rentee.ui.uploadedItems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.rentee.databinding.FragmentItemsUploadListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UploadedItemsListFragment : Fragment() {
    private lateinit var binding: FragmentItemsUploadListBinding
    private val uploadedItemsListViewModel: UploadedItemsListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemsUploadListBinding.inflate(inflater, container, false)
        context ?: return binding.root

        // LiveData needs the lifecycle owner
        binding.lifecycleOwner = this

        val adapter = UploadedItemListAdapter()
        binding.uploadedItemsList.adapter = adapter

        binding.fabUploadItem.setOnClickListener(View.OnClickListener {
            navigateToNewUploadItemPage()
        })

        subscribeUi(adapter)
        return binding.root
    }

    private fun subscribeUi(adapter: UploadedItemListAdapter) {
        uploadedItemsListViewModel.itemsList.observe(viewLifecycleOwner) { result ->
            adapter.submitList(result)
        }
    }

    private fun navigateToNewUploadItemPage() {
        val direction =
            UploadedItemsListFragmentDirections.actionNavigationUploadedListToUploadNewItemFragment()
        findNavController().navigate(direction)
    }
}
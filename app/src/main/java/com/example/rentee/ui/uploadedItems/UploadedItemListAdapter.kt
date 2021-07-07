package com.example.rentee.ui.uploadedItems

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rentee.data.Item
import com.example.rentee.databinding.ListItemUploadBinding

class UploadedItemListAdapter :
    ListAdapter<Item, UploadedItemListAdapter.ItemViewHolder>(WORDS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ListItemUploadBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

//    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val wordItemView: TextView = itemView.findViewById(R.id.tv_item_title)
//
//        fun bind(item: Item) {
//            wordItemView.text = item.name
//        }
//
//        companion object {
//            fun create(parent: ViewGroup): ItemViewHolder {
//                val view: View = LayoutInflater.from(parent.context)
//                    .inflate(R.layout.list_item_upload, parent, false)
//                return ItemViewHolder(view)
//            }
//        }
//    }

    class ItemViewHolder(
        private val binding: ListItemUploadBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
//                binding.plant?.let { plant ->
//                    navigateToPlant(plant, it)
//                }
            }
        }

//        private fun navigateToPlant(
//            plant: Plant,
//            view: View
//        ) {
//            val direction =
//                HomeViewPagerFragmentDirections.actionViewPagerFragmentToPlantDetailFragment(
//                    plant.plantId
//                )
//            view.findNavController().navigate(direction)
//        }

        fun bind(itemToBind: Item) {
            binding.apply {
                item = itemToBind
                executePendingBindings()
            }
        }
    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}
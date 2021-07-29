package com.example.rentee.ui.uploadedItems;

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.rentee.databinding.FragmentUploadNewItemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UploadNewItemFragment : Fragment() {
    private lateinit var binding: FragmentUploadNewItemBinding
    private val uploadNewItemViewModel: UploadNewItemViewModel by viewModels()
    val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                setPictureToView(data)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUploadNewItemBinding.inflate(inflater, container, false)
        context ?: return binding.root

        // LiveData needs the lifecycle owner
        binding.lifecycleOwner = this


        binding.ivClose.setOnClickListener {
            navigateUp()
        }

        binding.ivItemImage.setOnClickListener(View.OnClickListener {
            takePic()
        })

        binding.btnUploadItem.setOnClickListener(View.OnClickListener {
            val bitmap = (binding.ivItemImage.drawable as BitmapDrawable).bitmap
            uploadNewItemViewModel.uploadItem(bitmap, binding.etDescription.text.toString())
        })

        uploadNewItemViewModel.isUploaded.observe(viewLifecycleOwner, Observer {
            if(it){
                navigateUp()
            }

        })

        uploadNewItemViewModel.spinner.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.spinner.visibility = View.VISIBLE
            }else{
                binding.spinner.visibility = View.GONE
            }

        })

        return binding.root
    }

    private fun takePic() {
        val takePictureIntent = Intent(
            MediaStore.ACTION_IMAGE_CAPTURE
        )

        resultLauncher.launch(takePictureIntent)
    }

    private fun setPictureToView(data: Intent?) {
        val extras: Bundle? = data?.extras
        var imageBitmap = extras?.get("data") as Bitmap?
        binding.ivItemImage.setImageBitmap(imageBitmap)
    }

    private fun navigateUp() {
        findNavController().navigateUp()
    }
}

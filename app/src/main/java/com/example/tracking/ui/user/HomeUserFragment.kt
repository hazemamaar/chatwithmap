package com.example.tracking.ui.user

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.tracking.data.FireBaseHelper
import com.example.tracking.databinding.FragmentHomeUserBinding
import com.google.firebase.database.FirebaseDatabase


class HomeUserFragment : Fragment() {

    private var _binding: FragmentHomeUserBinding? = null
    private val binding get() = _binding!!
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inputTextLocation.setOnClickListener {

            val navigationIntentUri = Uri.parse("google.navigation:q=" + 0f + "," + 0f)
            val mapIntent = Intent(Intent.ACTION_VIEW, navigationIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        binding.callNow.setOnClickListener {
            binding.spinKit.visibility=View.VISIBLE
            if (binding.inputTextLocation.text.toString()
                    .isNotEmpty() && binding.inputTextCredit.text.toString()
                    .isNotEmpty() && binding.inputTextPain.text.toString().isNotEmpty()
            ) {
                FireBaseHelper.locationRef.setValue(binding.inputTextLocation.text.toString())
                FireBaseHelper.creditRef.setValue(binding.inputTextCredit.text.toString())
                FireBaseHelper.painRef.setValue(binding.inputTextPain.text.toString())
                binding.inputTextLocation.text?.clear()
                binding.inputTextCredit.text?.clear()
                binding.inputTextPain.text?.clear()
                binding.spinKit.visibility=View.GONE
                Toast.makeText(context,"Information Saved",Toast.LENGTH_LONG).show()
            }

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeUserBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}
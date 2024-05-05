package com.example.tracking.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tracking.data.FireBaseHelper
import com.example.tracking.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingActionButton.setOnClickListener {
            //here we drop the link in home fragment
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.app.goo.gl/kR7sj5iw6eLWkXgz7"))
            startActivity(browserIntent)
        }
        //1
        FireBaseHelper.locationRef.get().addOnCompleteListener {
            binding.inputTextLocation.setText(it.result.value.toString())
        }
        //4
        FireBaseHelper.diseasesRef .get().addOnCompleteListener {
            binding.diseasesTxt.text = "Chronic diseases: "+it.result.value.toString()
        }
        //5
        FireBaseHelper.phoneNumberRef.get().addOnCompleteListener {
            binding.phoneNumberTxt.text = "Contact Number: "+it.result.value.toString()
        }
        //6
        FireBaseHelper.ageRef.get().addOnCompleteListener {
            binding.ageTxt.text = "Patient age:"+it.result.value.toString()
        }
        //7
        FireBaseHelper.historyOfIllnessRef.get().addOnCompleteListener {
            binding.illnessTxt.text = "Current Illness: "+it.result.value.toString()
        }
        //9
        FireBaseHelper.nameRef.get().addOnCompleteListener {
            binding.NameTxt.text = "Patient name: "+it.result.value.toString()
        }
        binding.requestBtn.setOnClickListener {
            if(binding.inputTextLocation.text.toString().trim().isNotEmpty()){
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(binding.inputTextLocation.text.toString().trim()))
            startActivity(browserIntent)}
        }
//        if (FireBaseHelper.locationRef.get().result.value.toString().isNotEmpty()){
//            binding.inputTextPain.setText(FireBaseHelper.locationRef.get().result.value.toString())
//        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }


}
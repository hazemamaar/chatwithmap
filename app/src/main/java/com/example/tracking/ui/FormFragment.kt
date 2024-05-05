package com.example.tracking.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tracking.R
import com.example.tracking.data.FireBaseHelper
import com.example.tracking.databinding.FragmentChatBinding
import com.example.tracking.databinding.FragmentFormBinding
import com.example.tracking.navigateSafe
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult


class FormFragment : Fragment() {
    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var diseases: String
    lateinit var date: String
    lateinit var history: String
    lateinit var details: String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.getStart.setOnClickListener {
            binding.spinKit.visibility = View.VISIBLE
            if (
                binding.inputTextDiseases.text.toString().isNotEmpty() &&
                binding.inputTextBirthDate.text.toString().isNotEmpty() &&
                binding.inputTextHistory.text.toString().isNotEmpty() &&
                binding.inputTextIllness.text.toString().isNotEmpty() &&
                binding.inputTextPhoneNumber.text.toString().isNotEmpty()
            ) {
                diseases = binding.inputTextDiseases.text.toString()
                date = binding.inputTextBirthDate.text.toString()
                history = binding.inputTextHistory.text.toString()
                details = binding.inputTextIllness.text.toString()
                FireBaseHelper.diseasesRef.setValue(diseases)
                FireBaseHelper.ageRef.setValue(date)
                FireBaseHelper.historyOfIllnessRef.setValue(details)
                FireBaseHelper.medicalHistoryRef.setValue(history)
                FireBaseHelper.phoneNumberRef.setValue(binding.inputTextPhoneNumber.text.toString())
                updateUser()
            } else {
                Toast.makeText(context, "Please Fill All Information ...", Toast.LENGTH_SHORT)
                    .show()
            }
            binding.spinKit.visibility = View.GONE

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun updateUser() {
        val hashMap = HashMap<String, Any>()
        sharedPreferences = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)
        val id = sharedPreferences.getString("token", null)
        Log.e("hazemamaar", "updateUser: $id")
        if (id != null) {
            FireBaseHelper.databaseRef.child(id).get().addOnCompleteListener {
                hashMap["id"] = it.result.child("id").value.toString()
                hashMap["name"] = it.result.child("name").value.toString()
                FireBaseHelper.nameRef.setValue(it.result.child("name").value.toString())
                hashMap["rule"] = it.result.child("rule").value.toString().toBoolean()
                hashMap["isVisited"] = true
                hashMap["diseases"] = diseases
                hashMap["data"] = date
                hashMap["history"] = history
                hashMap["details"] = details
                FireBaseHelper.databaseRef
                    .child(id.toString()).setValue(hashMap).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navigateSafe(
                                R.id.action_formFragment_to_userActivity,
                                container = R.navigation.setup_nav
                            )
                        }
                    }
            }

        }


    }

}

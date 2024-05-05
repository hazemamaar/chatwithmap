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
import androidx.navigation.Navigation
import com.example.tracking.R
import com.example.tracking.data.FireBaseHelper
import com.example.tracking.databinding.FragmentLoginBinding
import com.example.tracking.model.OutCome
import com.example.tracking.navigateSafe

class LoginFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    // This property is only valid between onCreateView and
// onDestroyView.
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.goToRegister.setOnClickListener {
            navigateSafe(
                R.id.action_loginFragment_to_registerFragment,
                container = R.navigation.setup_nav
            )
        }
        binding.signInBtn.setOnClickListener {
            binding.spinKit.visibility=View.VISIBLE
            val email = binding.inputTextEmail.text.toString()
            val password = binding.inputTextPassword.text.toString()
            if (email.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Please, Enter your email...", Toast.LENGTH_SHORT)
                    .show();

            } else if (password.toString().isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please, Enter your password...",
                    Toast.LENGTH_SHORT
                ).show();

            } else if (password.toString().length < 6) {
                Toast.makeText(
                    requireContext(),
                    "Please, password must be at least 6 charchters.",
                    Toast.LENGTH_SHORT
                ).show();
            } else {
                FireBaseHelper.fireBaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { it ->
                        if (it.isSuccessful) {
                            FireBaseHelper.id = FireBaseHelper.fireBaseAuth.currentUser?.uid
                            sharedPreferences =
                                requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)
                            FireBaseHelper.databaseRef.child(FireBaseHelper.id!!).get()
                                .addOnCompleteListener {
                                    editor = sharedPreferences.edit()
                                    var hazem: OutCome = OutCome(
                                        it.result.child("id").value.toString(),
                                        it.result.child("name").value.toString(),
                                        it.result.child("rule").value.toString().toBoolean(),
                                        it.result.child("isVisited").value.toString().toBoolean()
                                    )
                                    Log.e("hazem", "onViewCreated: ${hazem}")
                                    editor.putString("token", it.result.key)
                                        .apply()
                                    editor.putString("name", hazem?.name)
                                        .apply()
                                    editor.putBoolean("rule", hazem.rule == true)
                                        .apply()
                                    editor.putBoolean("isVisited", hazem.isVisited == true)
                                        .apply()
                                    editor.commit()

                                    if (hazem?.isVisited == false && hazem.rule == false) {
                                        navigateSafe(
                                            R.id.action_loginFragment_to_formFragment,
                                            container = R.navigation.nav_graph
                                        )

                                    } else if(hazem?.isVisited==true&& hazem.rule==false){
                                        navigateSafe(
                                            R.id.action_loginFragment_to_userActivity,
                                            container = R.navigation.nav_graph
                                        )
                                    }else {
                                        navigateSafe(
                                            R.id.action_loginFragment_to_appActivity,
                                            container = R.navigation.nav_graph
                                        )
                                    }
                                    binding.spinKit.visibility=View.GONE
                                    Toast.makeText(context,"Login Done",Toast.LENGTH_SHORT).show()

                                }
                        } else {
                            Toast.makeText(requireContext(), "Log in failed", Toast.LENGTH_SHORT)
                                .show();
                            binding.spinKit.visibility=View.GONE

                        }
                    }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}
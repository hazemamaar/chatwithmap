package com.example.tracking.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tracking.R
import com.example.tracking.data.FireBaseHelper
import com.example.tracking.databinding.FragmentRegisterBinding
import com.example.tracking.navigateSafe
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.goToLogin.setOnClickListener {
            navigateSafe(
                R.id.action_registerFragment_to_loginFragment,
                container = R.navigation.setup_nav
            )
        }
        binding.signUp.setOnClickListener {
            binding.spinKit.visibility= View.VISIBLE
            val email = binding.inputTextEmail.text.toString()
            val password = binding.inputTextPassword.text.toString()
            val id = binding.inputTextID.text.toString()
            val name = binding.inputTextFullName.text.toString()
            if (email.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please, Enter your email...",
                    Toast.LENGTH_SHORT
                ).show()

            } else if (password.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please, Enter your password...",
                    Toast.LENGTH_SHORT
                ).show()

            } else if (password.length < 6) {
                Toast.makeText(
                    requireContext(),
                    "Please, password must be at least 6 charchters.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (name.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please, Enter your name...",
                    Toast.LENGTH_SHORT
                ).show()

            } else if (id.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please, Enter your National ID...",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                registerNewUser(name, email, password, id)
                Toast.makeText(
                    requireContext(),
                    "Registration Done",
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.spinKit.visibility=View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun registerNewUser(username: String, email: String, password: String, id: String) {
        FireBaseHelper.fireBaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                if (task.isSuccessful) {
                    val hashMap = HashMap<String, Any>()
                    hashMap["id"] = FireBaseHelper.fireBaseAuth.currentUser!!.uid
                    hashMap["name"] = username
                    hashMap["rule"] = binding.rememberMeCheckbox.isChecked
                    hashMap["isVisited"] = false
                    FireBaseHelper.databaseRef
                        .child(FireBaseHelper.fireBaseAuth.currentUser!!.uid)
                        .setValue(hashMap).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                navigateSafe(
                                    R.id.action_registerFragment_to_loginFragment,
                                    container = R.navigation.setup_nav
                                )
                            }
                        }
                } else {

                    Toast.makeText(
                        requireContext(),
                        "You can't Register with this email and password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}
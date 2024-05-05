package com.example.tracking.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.tracking.R
import com.example.tracking.data.FireBaseHelper
import com.example.tracking.databinding.FragmentLoginBinding
import com.example.tracking.databinding.FragmentProfileBinding
import com.example.tracking.navigateSafe
import com.example.tracking.popBack
import com.example.tracking.popUpCurrentFragment
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("name", null)
        binding.name.text = name
        editor = sharedPreferences.edit()
        binding.logoutContainer.setOnClickListener {
            editor.remove("token")
                .apply()
            editor.remove("name")
                .apply()
            editor.remove("rule")
                .apply()
            editor.remove("isVisited")
                .apply()
            editor.commit()
            FireBaseHelper.fireBaseAuth.signOut()
//            Navigation.findNavController(requireView()).clearBackStack(R.id.mainActivity)
            Navigation.findNavController(requireView()).navigate(ProfileFragmentDirections.actionProfileFragment2ToMainActivity2())




        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =FragmentProfileBinding.inflate(layoutInflater,container,false)
        return binding.root
    }


}
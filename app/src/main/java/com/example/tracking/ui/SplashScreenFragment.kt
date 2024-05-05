package com.example.tracking.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tracking.R
import com.example.tracking.navigateSafe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SplashScreenFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences =
            requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token",null)
        val rule = sharedPreferences.getBoolean("rule",false)
        val isVisited =sharedPreferences.getBoolean("isVisited",false)
        GlobalScope.launch {
            withContext(Dispatchers.Main){
                delay(3000).apply {
                    if (token!= null){
                    if (!isVisited && !rule) {
                        navigateSafe(
                            R.id.action_splashScreenFragment_to_formFragment,
                            container = R.navigation.nav_graph
                        )

                    } else if(isVisited && !rule){
                        navigateSafe(
                            R.id.action_splashScreenFragment_to_userActivity,
                            container = R.navigation.nav_graph
                        )
                    }else {
                        navigateSafe(
                            R.id.action_splashScreenFragment_to_appActivity,
                            container = R.navigation.nav_graph
                        )
                    }}else {
                        navigateSafe(
                            R.id.action_splashScreenFragment_to_loginFragment,
                            container = R.navigation.setup_nav
                        )
                    }
                }
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

}
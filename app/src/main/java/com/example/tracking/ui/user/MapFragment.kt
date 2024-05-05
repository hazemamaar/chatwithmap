package com.example.tracking.ui.user

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tracking.R

class MapFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // here also we drop the link in map fragment
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.app.goo.gl/kR7sj5iw6eLWkXgz7"))
        startActivity(browserIntent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

}

//to get apk follow
//1 go to build
// 2 go to build bundle
// 3 choice apk
// stay until finish
package com.example.tracking

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment


class BlynkFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // or you can replace **'this'** with your **ActivityName.this**
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=cloud.blynk"))
        startActivity(browserIntent)

//        openApp()
//        try {
//            val i: Intent? =
//                requireContext().packageManager.getLaunchIntentForPackage("com.eken.aiwit")
//            requireContext().startActivity(i)
//        } catch (e: NameNotFoundException) {
//            // TODO Auto-generated catch block
//        }
//

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_blynk, container, false)
    }

    private fun openApp() {
        val packageName = "cloud.blynk"
        startActivity(requireContext().getPackageManager().getLaunchIntentForPackage(packageName))
//        if (isAppInstalled(
//                requireActivity(),
//                packageName
//            )
//        ) startActivity(requireContext().getPackageManager().getLaunchIntentForPackage(packageName)) else Toast.makeText(
//            activity,
//            "App not installed",
//            Toast.LENGTH_SHORT
//        ).show()
    }

    fun isAppInstalled(activity: Activity, packageName: String?): Boolean {
        val pm = activity.packageManager
        try {
            pm.getPackageInfo(packageName!!, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: NameNotFoundException) {
        }
        return false
    }


}
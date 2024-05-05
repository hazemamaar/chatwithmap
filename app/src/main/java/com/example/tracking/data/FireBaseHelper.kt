package com.example.tracking.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

object FireBaseHelper {
    val fireBaseAuth = FirebaseAuth.getInstance()
    val databaseRef =
        FirebaseDatabase.getInstance().getReference("Users")
   val locationRef= FirebaseDatabase.getInstance().getReference("Location")
    val creditRef= FirebaseDatabase.getInstance().getReference("Credit")
    val nameRef= FirebaseDatabase.getInstance().getReference("Name")
    val phoneNumberRef= FirebaseDatabase.getInstance().getReference("PhoneNumber")
    val ageRef= FirebaseDatabase.getInstance().getReference("Age")
    val historyOfIllnessRef= FirebaseDatabase.getInstance().getReference("Illness")
    val medicalHistoryRef= FirebaseDatabase.getInstance().getReference("Medical")
    val diseasesRef = FirebaseDatabase.getInstance().getReference("Diseases")
    val painRef = FirebaseDatabase.getInstance().getReference("Diseases")
    var id:kotlin.String? = null

}
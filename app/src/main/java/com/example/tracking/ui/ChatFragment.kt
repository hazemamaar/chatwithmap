package com.example.tracking.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LayoutAnimationController
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tracking.R
import com.example.tracking.UserAdapter
import com.example.tracking.data.FireBaseHelper
import com.example.tracking.databinding.FragmentChatBinding
import com.example.tracking.model.OutCome
import com.example.tracking.navigateSafe
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ChatFragment : Fragment() {


    lateinit var userAdapter: UserAdapter
    lateinit var muser: ArrayList<OutCome>
    lateinit var userlist: ArrayList<String>
    var firebaseUser: FirebaseUser? = null
    var userid: String? = null


//     This property is only valid between onCreateView and
// onDestroyView.
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseUser = FireBaseHelper.fireBaseAuth.currentUser
        muser  = ArrayList()
        userlist =ArrayList()
        setUpRv()
        FirebaseDatabase.getInstance().getReference("Chatlist").child(firebaseUser?.uid!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (isAdded) {
                        userlist.clear()
                        for (snapshot in dataSnapshot.children) {
                            val chatlist= snapshot.child("id").value
                            Log.e("chatList", "onDataChange: $chatlist", )
                            userlist.add(chatlist.toString())
                        }
                        readUsers()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun readUsers() {
        FireBaseHelper.databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (isAdded) {
                    if (snapshot.exists()) {

                        for (snapshot in snapshot.children) {
                            val user = snapshot.getValue(OutCome::class.java)
                            if(FireBaseHelper.id != user?.id){
                                muser.add(user!!)
                            }
                        }
                        userAdapter.list=muser

                    } else {

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun setUpRv() = binding.apply {
        this.recycleView.apply {
            Log.e("hazem", "setUpRv: $muser")
            userAdapter = UserAdapter(context)
            adapter =userAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            //  addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }
}
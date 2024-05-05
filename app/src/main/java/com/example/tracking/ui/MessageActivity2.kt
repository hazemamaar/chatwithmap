package com.example.tracking.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tracking.R
import com.example.tracking.databinding.ActivityMessage2Binding
import com.example.tracking.model.Chat
import com.example.tracking.model.UserModel
import com.example.tracking.navigateSafe
import com.example.tracking.popBack
import com.example.tracking.popUpCurrentFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MessageActivity2 : AppCompatActivity() {
    var fuser: FirebaseUser? = null
    var reference: DatabaseReference? = null
    private lateinit var binding: ActivityMessage2Binding

    var messageAdapter: MessageAdapter? = null
    lateinit var mChat: MutableList<Chat>
    private val args: MessageActivityArgs by navArgs()
    lateinit var seenListener: ValueEventListener

    lateinit var userId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessage2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.back.setOnClickListener {
           popBack(R.navigation.nav_graph)
        }
        userId = intent.getStringExtra("USER_ID").toString()
        Log.e("userid", "onCreate: $userId")

        binding.recycleMessage.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        binding.recycleMessage.layoutManager = layoutManager


        fuser = FirebaseAuth.getInstance().currentUser

        binding.imageSend.setOnClickListener {
            val msg = binding.message.text.toString()
            if (!msg.equals("")){
                Toast.makeText(this, "Send Message", Toast.LENGTH_SHORT).show()

             sendMessage(fuser!!.uid, userId, msg)
            } else {
                Toast.makeText(this, "You can't send empty message", Toast.LENGTH_SHORT).show()
            }
            // kosongkan lagi
            binding.message.setText("")
        }

        // firebase
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId)
        reference!!.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user : UserModel? = dataSnapshot.getValue(UserModel::class.java)
                binding.username.text = user!!.name
//                if (user?.imageURL.equals("default")){
//                    profile_image.setImageResource(R.mipmap.ic_launcher)
//                } else {
//                    Glide.with(applicationContext).load(user?.imageURL).into(profile_image)
//                }

                readMessages(fuser?.uid, userId)
            }
        })
    }


    private fun readMessages(myId: String?, userId: String?) {
        mChat = arrayListOf()

        reference = FirebaseDatabase.getInstance().getReference("Chats")
        reference!!.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mChat.clear()

                for (snapshot: DataSnapshot in dataSnapshot.children){
                    val chat = snapshot.getValue(Chat::class.java)
                    if (chat?.receiver.equals(fuser?.uid) && chat?.sender.equals(userId) || chat?.receiver.equals(userId) && chat?.sender.equals(myId)){
                        mChat.add(chat!!)
                    }
                }
                // adapter
                messageAdapter = MessageAdapter(this@MessageActivity2)
                messageAdapter?.list = mChat
                binding.recycleMessage.adapter = messageAdapter
            }

        })
    }


}


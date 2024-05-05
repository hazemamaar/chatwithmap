package com.example.tracking

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tracking.model.Chat
import com.example.tracking.model.OutCome
import com.example.tracking.ui.MessageActivity2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserAdapter( val context: Context) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var theLastMessage: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.chat_item, parent, false)
        )
    }
    var list: List<OutCome>
        get() = differ.currentList
        set(value) = differ.submitList(value)
    private val differCallBack by lazy {
        object : DiffUtil.ItemCallback<OutCome>() {
        override fun areItemsTheSame(
            oldItem: OutCome,
            newItem: OutCome
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(
            oldItem: OutCome,
            newItem: OutCome
        ): Boolean {
            return oldItem == newItem
        }
    }
    }
    val differ = AsyncListDiffer(this, differCallBack)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = list[position]
        holder.username.text = user.name
        holder.container.setOnClickListener {
            val intent = Intent(context, MessageActivity2::class.java)
            intent.putExtra("USER_ID", user.id)
            context.startActivity(intent)
        }
        lastMessage(user.id!!, holder.lastMessage)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.name)
        val lastMessage: TextView = itemView.findViewById(R.id.message)
        val container: ConstraintLayout = itemView.findViewById<ConstraintLayout>(R.id.container)
    }

    private fun lastMessage(userid: String, last_msg: TextView?) {
        theLastMessage = "default"

        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val reference = FirebaseDatabase.getInstance().getReference("Chats")
        reference.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val chat = snapshot.getValue(Chat :: class.java)

                    if (firebaseUser != null && chat != null){

                        if (chat.receiver?.equals(firebaseUser.uid)!! && chat.sender.equals(userid)){
                            theLastMessage = chat.message
                        }
                    }
                }

                when (theLastMessage){
                    "default" -> last_msg?.text = "No Message"
                    else -> {
                        last_msg?.text = theLastMessage
                    }
                }

                theLastMessage = "default"
            }

        })
    }

    private var onItemClickListener: ((OutCome) -> Unit)? = null

    fun setOnItemClickListener(listener: (OutCome) -> Unit) {
        onItemClickListener = listener
    }

}

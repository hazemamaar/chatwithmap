package com.example.tracking.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tracking.R
import com.example.tracking.data.FireBaseHelper
import com.example.tracking.model.Chat
import com.example.tracking.model.OutCome
import com.example.tracking.ui.MessageAdapter.ViewHolder


class MessageAdapter(context: Context) :
    RecyclerView.Adapter<ViewHolder>() {

    var context: Context

    init {
        this.context = context
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return if (viewType == MES_TYPE_RIGHT) {
           ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false)
            )
        } else {
            ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false)
            )
        }
    }
    var list: List<Chat>
        get() = differ.currentList
        set(value) = differ.submitList(value)
    private val differCallBack by lazy {
        object : DiffUtil.ItemCallback<Chat>() {
            override fun areItemsTheSame(
                oldItem: Chat,
                newItem: Chat
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(
                oldItem: Chat,
                newItem: Chat
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)
    override fun onBindViewHolder(
        holder: MessageAdapter.ViewHolder,
        position: Int
    ) {
        holder.massge.setText(list[position].message)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var profile_image: ImageView

        var massge: TextView

        init {
            profile_image = itemView.findViewById<ImageView>(R.id.profile_image)
            massge = itemView.findViewById<TextView>(R.id.show_message)

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].sender == FireBaseHelper.id) {
            MES_TYPE_RIGHT
        } else {
            MES_TYPE_LEFT
        }
    }

    companion object {
        const val MES_TYPE_LEFT = 0
        const val MES_TYPE_RIGHT = 1
    }
}
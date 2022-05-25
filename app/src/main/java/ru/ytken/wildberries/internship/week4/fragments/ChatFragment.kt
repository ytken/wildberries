package ru.ytken.wildberries.internship.week4.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.element_chat_my_message.view.*
import kotlinx.android.synthetic.main.element_chat_talker_message.view.*
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.coroutines.flow.collectLatest
import ru.ytken.wildberries.internship.week4.MainViewModel
import ru.ytken.wildberries.internship.week4.R
import ru.ytken.wildberries.internship.week4.models.ChatData

class ChatFragment: Fragment(R.layout.fragment_chat) {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var chatData: ChatData
    private lateinit var listMessagesAdapter: MessagesRecyclerViewAdapter

    companion object {
        val NUMBER_OF_CHAT = "NUMBER_OF_CHAT"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val indexOfChat = arguments?.getInt(NUMBER_OF_CHAT)
        if (indexOfChat != null) {
            chatData = viewModel.getChatDataByIndex(indexOfChat)

            textViewName.text = chatData.talkerName

            recyclerViewChatMessages.apply {
                layoutManager = LinearLayoutManager(context).apply {
                    stackFromEnd = true
                    reverseLayout = false
                }
                listMessagesAdapter = MessagesRecyclerViewAdapter()
                adapter = listMessagesAdapter
            }
            lifecycleScope.launchWhenCreated {
                viewModel.getMessagesListData(indexOfChat).collectLatest {
                    listMessagesAdapter.submitData(it)
                }
            }
            buttonUpdateChat.setOnClickListener {
                viewModel.updateMessages(indexOfChat)
                listMessagesAdapter.notifyDataSetChanged()
            }
        }
    }

    class MessagesRecyclerViewAdapter: PagingDataAdapter<Pair<Boolean, String>,
            MessagesRecyclerViewAdapter.MyViewHolder>(DiffUtilCallBack()) {
        class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
            fun bind(data: Pair<Boolean, String>) {
                if(itemViewType == 1)
                    itemView.textViewMyMessage.text = data.second
                else
                    itemView.textViewTalkerMessage.text = data.second
            }
        }

        override fun getItemViewType(position: Int): Int {
            return if (getItem(position)?.first == true) 1 else 0
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.bind(getItem(position)!!)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val inflater = if (viewType == 1)
                LayoutInflater.from(parent.context).inflate(R.layout.element_chat_my_message, parent, false)
            else
                LayoutInflater.from(parent.context).inflate(R.layout.element_chat_talker_message, parent, false)
            return MyViewHolder(inflater)
        }

        class DiffUtilCallBack: DiffUtil.ItemCallback<Pair<Boolean, String>>() {
            override fun areItemsTheSame(
                oldItem: Pair<Boolean, String>,
                newItem: Pair<Boolean, String>
            ): Boolean {
                return oldItem.first == newItem.first && oldItem.second == newItem.second
            }

            override fun areContentsTheSame(
                oldItem: Pair<Boolean, String>,
                newItem: Pair<Boolean, String>
            ): Boolean {
                return oldItem.second == newItem.second
            }

        }

    }
}
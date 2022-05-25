package ru.ytken.wildberries.internship.week4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.element_chat_list.view.*
import kotlinx.android.synthetic.main.fragment_chat_list.*
import ru.ytken.wildberries.internship.week4.R
import ru.ytken.wildberries.internship.week4.models.ChatData
import androidx.fragment.app.activityViewModels
import androidx.paging.AsyncPagingDataDiffer
import kotlinx.coroutines.flow.collectLatest
import ru.ytken.wildberries.internship.week4.MainViewModel

class ChatListFragment: Fragment(R.layout.fragment_chat_list) {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var listChatsAdapter: RecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewChatList.apply {
            layoutManager = LinearLayoutManager(context)
            listChatsAdapter = RecyclerViewAdapter()
            adapter = listChatsAdapter
        }
        lifecycleScope.launchWhenCreated {
            viewModel.getListData().collectLatest {
                listChatsAdapter.submitData(it)
            }
        }

        swipeRefreshChatLayout.setOnRefreshListener {
            viewModel.changeListData()
            listChatsAdapter.refresh()
            swipeRefreshChatLayout.isRefreshing = false
        }
    }

    class RecyclerViewAdapter: PagingDataAdapter<ChatData, RecyclerViewAdapter.MyViewHolder>(DiffUtilCallBack()) {
        class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
            private val MAX_MESSAGE_LENGTH = 32

            fun bind(data: ChatData) {
                itemView.textViewHeadingChat.text = data.talkerName

                val message = data.dialogList[data.dialogList.size - 1].second
                val fullMessage =
                    if (data.dialogList.last().first) "Вы: $message"
                    else "${data.talkerName}: $message"
                val cutMessage = if (fullMessage.length < MAX_MESSAGE_LENGTH)
                    fullMessage else "${fullMessage.subSequence(0, MAX_MESSAGE_LENGTH - 1)}..."
                itemView.textViewDescriptionChat.text = cutMessage

                val numberOfNonViewedMessages = data.numberOfNonViewedMessages
                if (numberOfNonViewedMessages > 0) {
                    itemView.textViewNonViewedMessages.text = numberOfNonViewedMessages.toString()
                    itemView.textViewNonViewedMessages.visibility = View.VISIBLE
                }
                else
                    itemView.textViewNonViewedMessages.visibility = View.INVISIBLE
            }
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.bind(getItem(position)!!)
            val fragment = ChatFragment().apply {
                arguments = Bundle().apply {
                    putInt(ChatFragment.NUMBER_OF_CHAT, position)
                }
            }
            holder.itemView.setOnClickListener {
                (it.context as AppCompatActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val inflater = LayoutInflater.from(parent.context).inflate(R.layout.element_chat_list, parent, false)
            return MyViewHolder(inflater)
        }

        class DiffUtilCallBack: DiffUtil.ItemCallback<ChatData>() {
            override fun areItemsTheSame(oldItem: ChatData, newItem: ChatData): Boolean {
                return oldItem.talkerName == newItem.talkerName
            }

            override fun areContentsTheSame(oldItem: ChatData, newItem: ChatData): Boolean {
                return oldItem.talkerName == newItem.talkerName &&
                        oldItem.dialogList == newItem.dialogList &&
                        oldItem.numberOfNonViewedMessages == newItem.numberOfNonViewedMessages
            }

        }

    }

}
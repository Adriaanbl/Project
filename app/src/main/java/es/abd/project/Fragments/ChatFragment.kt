package es.abd.project.Fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import es.abd.project.ChatUtils.ChatAdapter
import es.abd.project.FirebaseUtils.FirestoreManager
import es.abd.project.R
import es.abd.project.databinding.ChatFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Timestamp
import java.time.Instant
import java.util.Date

class ChatFragment : Fragment() {

    private lateinit var binding: ChatFragmentBinding
    private lateinit var messages: MutableList<es.abd.project.ChatUtils.Message>
    private lateinit var mAdapter: ChatAdapter


    private val firestoreManager: FirestoreManager by lazy { FirestoreManager() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ChatFragmentBinding.inflate(inflater, container, false)
        val prefs = requireContext().getSharedPreferences("es.abd.project_preferences", Context.MODE_PRIVATE)

        val colorChat = prefs.getString("color", "")
        val colorc = Color.parseColor(colorChat)
        requireActivity().findViewById<View>(R.id.chat_item)?.setBackgroundColor(colorc)

        setRecyclerView()

        val user = prefs.getString("username","")



        binding.messageIpnut.setOnEditorActionListener{ _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                event != null && event.action == KeyEvent.ACTION_DOWN &&
                event.keyCode == KeyEvent.KEYCODE_ENTER) {

                lifecycleScope.launch ( Dispatchers.IO ){
                    firestoreManager.addMessage(es.abd.project.ChatUtils.Message(
                        name = user.toString(),
                        text = binding.messageIpnut.text.toString(),
                        time = com.google.firebase.Timestamp.now()
                    ))
                    withContext(Dispatchers.Main){
                        binding.messageIpnut.text?.clear()
                    }
                }

            }
            false
        }

        return binding.root
    }


    private fun setRecyclerView(){
        messages = mutableListOf()
        binding.recyclerChat.layoutManager = GridLayoutManager(requireContext(), 1)
        mAdapter = ChatAdapter(requireContext(), messages)
        binding.recyclerChat.adapter = mAdapter


        lifecycleScope.launch (Dispatchers.IO){
            firestoreManager.getMessagesFlow()
                .collect{ messagesUpdated ->
                    messages.clear()
                    messages.addAll(messagesUpdated)
                    withContext(Dispatchers.Main){
                        mAdapter.notifyDataSetChanged()
                    }
                }

        }

        messages
    }

}
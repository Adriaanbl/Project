package es.abd.project.ChatUtils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.abd.project.R

class ChatAdapter(private val context: Context,
                   private val items: MutableList<Message>
) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.message_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bindItem(item)
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val msgUser: TextView = view.findViewById(R.id.message_user_txt)
        private val msgTime: TextView = view.findViewById(R.id.message_time_txt)
        private val msgText: TextView = view.findViewById(R.id.message_txt)

        fun bindItem(item: Message){
            msgUser.text = item.name
            msgTime.text = item.time.toString()
            msgText.text = item.text
        }
    }

  
}


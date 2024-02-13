package es.abd.project.ChatUtils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.abd.project.R

class ChatAdapter(private val context: Context,
                   private val items: MutableList<Message>,
                   private val mListener: NotesAdapterListener
) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bindItem(item)
        holder.itemView.setOnClickListener { mListener.onNoteClick(item) }
        holder.itemView.setOnLongClickListener { mListener.onNoteLongClick(item) }
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        private val tvContent: TextView = view.findViewById(R.id.tvContent)

        fun bindItem(item: Note){
            tvTitle.text = item.title
            tvContent.text = item.content
        }
    }

    interface NotesAdapterListener{
        fun onNoteClick(note: Note)
        fun onNoteLongClick(note: Note): Boolean
    }
}


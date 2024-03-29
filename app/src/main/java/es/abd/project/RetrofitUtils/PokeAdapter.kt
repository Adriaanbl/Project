package es.abd.project.RetrofitUtils

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.abd.project.R

class PokeAdapter (private val pokemons: MutableList<Pokemon>)
    : RecyclerView.Adapter<PokeAdapter.PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokemonViewHolder(layoutInflater.inflate(R.layout.pokemon_item, parent, false))
    }

    override fun getItemCount(): Int = pokemons.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = pokemons[position]
        holder.bindItem(item)

        holder.itemView.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
            holder.itemView.context.startActivity(intent)
        }

    }

    class PokemonViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val pokemon_name: TextView = view.findViewById(R.id.pokemon_name)
        private val pokemon_url: TextView = view.findViewById(R.id.pokemon_url)

        fun bindItem(pokemon: Pokemon){
            pokemon_name.text = pokemon.name
            pokemon_url.text = pokemon.url
        }

    }
}


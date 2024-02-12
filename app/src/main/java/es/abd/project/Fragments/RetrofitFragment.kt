package es.abd.project.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import es.abd.project.R
import es.abd.project.Resources.PokeAdapter
import es.abd.project.Resources.Pokemon
import es.abd.project.Resources.PokemonService
import es.abd.project.Resources.RetrofitObject
import es.abd.project.databinding.LoginFragmentBinding
import es.abd.project.databinding.RetrofitFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RetrofitFragment : Fragment() {

    private lateinit var binding: RetrofitFragmentBinding
    private lateinit var pokeAdapter: PokeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RetrofitFragmentBinding.inflate(inflater, container, false)

        return binding.root

    }

    private fun setUpRecyclerView(){

        lifecycleScope.launch(Dispatchers.IO) {
            val call = RetrofitObject.getInstance()
                .create(PokemonService::class.java).getPokemon()

            val response = call.body()
            withContext(Dispatchers.Main){
                val pokemonAdapter = PokeAdapter(response)

                binding.recyclerPokemon.adapter = pokemonAdapter

                binding.recyclerPokemon.layoutManager = GridLayoutManager(requireContext(),2)


            }
        }



    }

}
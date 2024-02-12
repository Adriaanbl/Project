package es.abd.project.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import es.abd.project.RetrofitUtils.PokeAdapter
import es.abd.project.RetrofitUtils.PokemonService
import es.abd.project.RetrofitUtils.RetrofitObject
import es.abd.project.databinding.RetrofitFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RetrofitFragment : Fragment() {

    private lateinit var binding: RetrofitFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RetrofitFragmentBinding.inflate(inflater, container, false)

        setUpRecyclerView()

        return binding.root

    }

    private fun setUpRecyclerView() {
        lifecycleScope.launch(Dispatchers.IO) {
            val call = RetrofitObject.getInstance()
                .create(PokemonService::class.java).getPokemon()

            val response = call.body()
            withContext(Dispatchers.Main) {
                if (response != null) {
                    val pokemonList = response.results.toMutableList()
                    val pokemonAdapter = PokeAdapter(pokemonList)
                    binding.recyclerPokemon.adapter = pokemonAdapter
                    binding.recyclerPokemon.layoutManager = GridLayoutManager(requireContext(), 2)
                } else {

                }
            }
        }
    }



}
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import es.abd.project.Database.AppDB
import es.abd.project.Database.PokemonDao
import es.abd.project.RetrofitUtils.PokeAdapter
import es.abd.project.RetrofitUtils.Pokemon
import es.abd.project.RetrofitUtils.PokemonService
import es.abd.project.RetrofitUtils.RetrofitObject
import es.abd.project.databinding.RetrofitFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RetrofitFragment : Fragment() {

    private lateinit var binding: RetrofitFragmentBinding
    private lateinit var db: AppDB
    private lateinit var pokemonAdapter: PokeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RetrofitFragmentBinding.inflate(inflater, container, false)

        binding.recyclerPokemon.layoutManager = GridLayoutManager(requireContext(), 2)
        val prefs = requireContext().getSharedPreferences(
            "es.abd.project_preferences",
            Context.MODE_PRIVATE
        )
        val offline: Boolean = prefs.getBoolean("offline", false)

        if (!isConnectedToInternet() && !offline) {
            loadFromDatabase()
        }else{
            setUpRecyclerView()
        }

        return binding.root

    }

    private fun loadFromDatabase() {
        db = Room.databaseBuilder(requireContext(), AppDB::class.java, "POKEMONDB").build()
        lifecycleScope.launch(Dispatchers.IO) {
            db.pokemonDao().deleteAll()
            val lista = db.pokemonDao().getAll()
            withContext(Dispatchers.Main) {
                InsertPokemonRoom(lista)
                pokemonAdapter = PokeAdapter(lista)
                binding.recyclerPokemon.adapter = pokemonAdapter
            }
        }
    }

    private fun setUpRecyclerView() {
        lifecycleScope.launch(Dispatchers.IO) {
            val call = RetrofitObject.getInstance()
                .create(PokemonService::class.java).getPokemon()

            val response = call.body()
            withContext(Dispatchers.Main) {
                if (response != null) {
                    val pokemonList = response.results.toMutableList()
                    pokemonAdapter = PokeAdapter(pokemonList)
                    binding.recyclerPokemon.adapter = pokemonAdapter
                } else {

                }
            }
        }
    }

    fun InsertPokemonRoom(lista: List<Pokemon>){

        val pokelistRoom: MutableList<Pokemon> = mutableListOf()

        lifecycleScope.launch (Dispatchers.IO) {
            lista.forEach {
                val pokemon = Pokemon(name = it.name, url = it.url)
                pokelistRoom.add(pokemon)
            }
            withContext(Dispatchers.Main){
                db.pokemonDao().insertAll(pokelistRoom)
            }
        }

    }

    private fun isConnectedToInternet(): Boolean {
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo?.isConnected ?: false
        }
    }



}
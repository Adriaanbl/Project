package es.abd.project.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import es.abd.project.RetrofitUtils.Pokemon

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon")
    suspend fun getAll(): MutableList<Pokemon>

    @Insert
    suspend fun insertAll(pokemonList: MutableList<Pokemon>)

    @Query("DELETE FROM pokemon")
    suspend fun deleteAll()
}
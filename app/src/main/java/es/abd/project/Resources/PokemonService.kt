package es.abd.project.Resources

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {
    @GET("pokemon")
    suspend fun getPokemon(): Response<Pokemon>
}

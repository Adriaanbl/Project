package es.abd.project.RetrofitUtils

import retrofit2.Response
import retrofit2.http.GET

interface PokemonService {
    @GET("pokemon")
    suspend fun getPokemon(): Response<PokemonResponse>
}

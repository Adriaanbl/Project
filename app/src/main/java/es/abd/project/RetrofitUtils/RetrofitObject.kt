package es.abd.project.RetrofitUtils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitObject {

    companion object{
        private var instance: Retrofit? = null

        fun getInstance(): Retrofit{
            synchronized(this) {
                if (instance == null) {
                    instance = Retrofit.Builder()
                        .baseUrl("https://pokeapi.co/api/v2/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
                return instance!!
            }
        }
    }
}


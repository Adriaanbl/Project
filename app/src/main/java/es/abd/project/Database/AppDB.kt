package es.abd.project.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import es.abd.project.RetrofitUtils.Pokemon

@Database(
    entities = [Pokemon::class],
    version = 1
)
abstract class AppDB : RoomDatabase() {
    abstract fun pokemonDao() : PokemonDao

}
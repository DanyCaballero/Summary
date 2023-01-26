package com.example.summary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.summary.network.Pokemon
import com.example.summary.network.PokemonApi
import kotlinx.coroutines.launch
import java.lang.Exception

enum class PokemonApiStatus { LOADING, ERROR, DONE }

class AppViewModel: ViewModel() {
    private val _status = MutableLiveData<PokemonApiStatus>()
    val status: LiveData<PokemonApiStatus> = _status

    private val _pokemons = MutableLiveData<List<Pokemon>>()
    val pokemons: LiveData<List<Pokemon>> = _pokemons

    init {
        getPokemons()
    }

    private fun getPokemons() {
        viewModelScope.launch { //Coroutine
            _status.value = PokemonApiStatus.LOADING
            try {
                _pokemons.value = PokemonApi.retrofitService.getPokemons()
                _status.value = PokemonApiStatus.DONE
            } catch (e: Exception) {
                _status.value = PokemonApiStatus.ERROR
                _pokemons.value = listOf() //Clears the RecyclerView
            }
        }
    }
}
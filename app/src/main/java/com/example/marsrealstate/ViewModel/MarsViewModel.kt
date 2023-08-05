package com.example.marsrealstate.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.marsrealstate.Model.Local.MarsDatabase
import com.example.marsrealstate.Model.MarsRepository
import com.example.marsrealstate.Model.Remote.MarsRealState
import kotlinx.coroutines.launch

class MarsViewModel(application: Application): AndroidViewModel(application) {

    private val repository: MarsRepository

    //en le repositorio hay dos funciones para obtener datos de inet
    //lo primero es representar lo recibido de inet
    val liveDataFromInternet: LiveData<List<MarsRealState>>
    //referenia a todos los terrenos de marte
    val allMars: LiveData<List<MarsRealState>>

    //inicializamos variables, tenemos que indicarles de donde vienen

    init {
        //referenciamos al dao en la base datos(abstract fun getMArsDao())
        val MarsDao=MarsDatabase.getDatabase(application).getMarsDao()
        //instanciamos el repositorio y le pasamos el dao
        repository= MarsRepository(MarsDao)

        //usando el metodo sin coroutines fetchDataMars()
        liveDataFromInternet= repository.fetchDataMars()

        allMars=repository.listAllMars
    }

    //variable para seleccionar terreno por ej en un adapter
    private var selectedMarsTerrain: MutableLiveData<MarsRealState> = MutableLiveData()

    //funcion para selccionar
    fun selected(marsTerrain: MarsRealState){
        selectedMarsTerrain.value=marsTerrain
    }

     fun insertMars(mars: MarsRealState)=viewModelScope.launch{
        repository.insertMars(mars)
    }

     fun updateMars(mars: MarsRealState)=viewModelScope.launch{
        repository.updateMars(mars)
    }

    fun deleteMars(mars: MarsRealState)=viewModelScope.launch{
        repository.deleteMars(mars)
    }

    fun deleteAllMars()= viewModelScope.launch{
        repository.deleteAllMars()
    }

    /*fun getMarsByType(type: String): LiveData<MarsRealState>{
        return repository.getMarsByType(type)
    }

    fun getMarsById(id: String): LiveData<MarsRealState>{
        return repository.getMarsById(id)
    }*/







}
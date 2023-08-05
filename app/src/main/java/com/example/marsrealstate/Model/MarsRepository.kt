package com.example.marsrealstate.Model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.marsrealstate.Model.Local.MarsDao
import com.example.marsrealstate.Model.Remote.MarsRealState
import com.example.marsrealstate.Model.Remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


//el repositorio se comunica con el dao y la interfaz api
class MarsRepository(private val marsDao: MarsDao) {

    //necesitamos retrofit, hacemos una variable que llama a la funcion que hace la
    // conexion en RetrofitClient
    private val retrofitClient = RetrofitClient.getRetroFit()

    //variable que hace referencia al pojo y a la respuesta que se recibe
    val dataFromInternet = MutableLiveData<List<MarsRealState>>()


    //funcion sin coroutine desde la api interfaz, y quien maneja a la api es el retrofit client
    //por lo tanto para acceder a la interfaz MarsApi  usamos a retrofitclient
    fun fetchDataMars(): LiveData<List<MarsRealState>> {
        //dejamos en cola al callback y le pasamos un objeto que traiga la lista
        retrofitClient.fetchMarsData().enqueue(object : retrofit2.Callback<List<MarsRealState>> {
            //sobreescribimos funcion on response para hacer un llamado a lo que necesitamos
            override fun onResponse(

                call: Call<List<MarsRealState>>,
                response: Response<List<MarsRealState>>
            ) {
                //segun la respuesta hacemos un when
                when (response.code()) {

                    //aca agregamos los códigos de respuestas
                    //en respuesta correcta traemos el cuerpo de la respuesta
                    in 200..299 -> dataFromInternet.value = response.body()
                    //si la respuesta no es favorable un log para ver que es lo que trae
                    in 300..301 -> Log.d("Repo", "${response.body()} --- ${response.errorBody()}")
                    //en caso contrario
                    else -> Log.d("Repo", "${response.body()} --- ${response.errorBody()}")

                }

            }

            //sobreescribimos la funcion onfailure
            override fun onFailure(call: Call<List<MarsRealState>>, t: Throwable) {
                Log.d("Repo", "${t.message}")
            }

        })

        //finalmente retornamos los datos de internet, es decir la lista de marsrealstate
        return dataFromInternet
    }

    /**VERSION CON COROUTINE*/

    suspend fun fetchDataFromInternetCoroutines() {

        //estamos sacando desde internet la respuesta, haremos un try and catch

        try {

            val response = retrofitClient.fetchMarsDataCoroutine()
            //segun la respuesta hacemos un when
            when (response.code()) {

                //aca agregamos los códigos de respuestas
                //en respuesta correcta traemos el cuerpo de la respuesta
                in 200..299 -> response?.body().let {
                    //si la respuesta es favorable
                    if (it != null) {
                        //llamamos al dao e insertamos todos los datos que vienen de inet
                        marsDao.insertAllMars(it)
                    }
                }
                //Otro caso
                in 300..301 -> Log.d("Repo", "${response.body()} --- ${response.errorBody()}")
            }
            //el catch toma el error

        } catch (t: Throwable) {
            Log.d("Repo", "${t.message}")

        }


    }

    //finalmente llamamos todas las funciones del dao


    /*fun getTerrainByid(id: String): LiveData<MarsRealState>{
        return getTerrainByid(id)
    }*///Esta funcion no está en el dao,

    //funcion para traer todos los terrenos
    val listAllMars: LiveData<List<MarsRealState>> = marsDao.getAllMars()

    suspend fun insertMars(mars: MarsRealState){
        marsDao.insertMars(mars)
    }

    suspend fun updateMars(mars: MarsRealState){
        marsDao.updateMars(mars)
    }

    suspend fun deleteMars(mars: MarsRealState){
        marsDao.deleteMars(mars)
    }

    suspend fun deleteAllMars(){
        marsDao.deleteAllMars()
    }

   /* fun getMarsByType(type: String): LiveData<MarsRealState>{
        return marsDao.getMarsByType(type)
    }

    fun getMarsById(id: String): LiveData<MarsRealState>{
        return marsDao.getMarsById(id)
    }*/
}

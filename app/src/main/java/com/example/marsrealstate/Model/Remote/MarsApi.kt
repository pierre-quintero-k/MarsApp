package com.example.marsrealstate.Model.Remote

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET


//la api tiene la responsabilidad de implementar los verbos(get, put etc) a la api

interface MarsApi {


    //las solicitudes se hacen con anotaciones, la idea es dar las distintas direcciones a la
    //url base

    @GET("realestate")
    //aca estamos llamando a los datos de la api y de ahi los obtenemos, fijarse que sea call de
    //de retrofit(es el que ofrece lista), llamamos a la lista de todos los terrenos, que es lo que
    //arroja /realstate en la api de marte
    fun fetchMarsData(): Call<List<MarsRealState>>//funcion sin corrutina, version segura

    @GET("realstate")
    //imagino que esta obtiene respuesta cuando despierta
    suspend fun fetchMarsDataCoroutine(): Response<List<MarsRealState>>

    /** entonces: Call sin coroutine y response con coroutine(suspen fun)
     */
}
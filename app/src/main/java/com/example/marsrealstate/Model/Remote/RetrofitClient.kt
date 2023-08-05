package com.example.marsrealstate.Model.Remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


//Esta es la clase que hace la conexion
class RetrofitClient {

    //se hace un companion object para poder instanciar en cualquier lugar
    companion object{


        //creamos una variable constante que va a indicar la url base de la api remota

        private const val BASE_URL="https://android-kotlin-fun-mars-server.appspot.com/"

        //funcion para conectar a esa base url, hereda de la interfaz api porque es quien tiene las
        //solicitudes
        fun getRetroFit(): MarsApi{
            //variable de tipo retrofit builder
            val retrofit= Retrofit.Builder()
                .baseUrl(BASE_URL)
                    //transformamos los datos recibidos
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            //en el retorno entregamos la interfaz que es la que tiene los verbos y donde hacer el get
            return retrofit.create(MarsApi::class.java)

        }
    }
}
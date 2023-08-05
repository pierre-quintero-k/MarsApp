package com.example.marsrealstate.Model.Local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.marsrealstate.Model.Remote.MarsRealState


@Dao
interface MarsDao {

    //para insertar un terreno
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMars(mars: MarsRealState)

    @Insert
    suspend fun insertAllMars(mars: List<MarsRealState>)

    @Update
    suspend fun updateMars(mars: MarsRealState)

    @Delete
    suspend fun deleteMars(mars: MarsRealState)

    @Query("DELETE FROM mars_table")
    suspend fun deleteAllMars()

    @Query("SELECT * FROM mars_table ORDER BY id DESC")
    fun getAllMars(): LiveData<List<MarsRealState>>

    //funcion para traer por tipo
  /*  @Query("SELECT * FROM mars_table WHERE type=type LIMIT 1")
    //recibimos un tipo que es string y la respuesta será un terreno envuelto en Livedata
    fun getMarsByType(type: String): LiveData<MarsRealState>

    @Query("SELECT * FROM mars_table WHERE id=id")
    //recibimos un tipo que es string y la respuesta será un terreno envuelto en Livedata
    fun getMarsById(id: String): LiveData<MarsRealState>
*/
    

}
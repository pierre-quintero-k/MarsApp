package com.example.marsrealstate.Model.Local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.marsrealstate.Model.Remote.MarsRealState


@Database(entities= [MarsRealState::class], version = 1)
abstract class MarsDatabase: RoomDatabase() {

    abstract fun getMarsDao(): MarsDao

    companion object {
        // ESTA VARIABLE ESTE SIEMPRE DISPONIBLE
        @Volatile
        private var INSTANCE: MarsDatabase? = null


        fun getDatabase(context: Context): MarsDatabase {
            val tempInntance = INSTANCE
            if (tempInntance != null) {

                return tempInntance
            }


            synchronized(this) {
                val instance = Room.databaseBuilder(
                    // la bade datos sea una para toda la app
                    context.applicationContext,
                    MarsDatabase::class.java,
                    //esta en Remote, se hizo una sola para localy remoto
                    "MarsRealState"
                )
                    .build()
                INSTANCE = instance
                return instance

            }
        }
    }

}
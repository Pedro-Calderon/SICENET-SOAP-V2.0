package com.example.marsphotos
import android.app.Application
import com.example.marsphotos.DataBase.DatabaseSicenet
class AplicacionSicenet : Application(){

    val database1: DatabaseSicenet by lazy { DatabaseSicenet.getDatabase(this
    ) }

}
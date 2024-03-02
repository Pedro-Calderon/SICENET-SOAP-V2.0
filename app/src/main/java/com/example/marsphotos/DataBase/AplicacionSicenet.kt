package com.example.marsphotos.DataBase
import android.app.Application
import com.example.marsphotos.DataBase.DatabaseSicenet
class AplicacionSicenet : Application(){
    val database: DatabaseSicenet by lazy { DatabaseSicenet.getDatabase(this
    ) }

}
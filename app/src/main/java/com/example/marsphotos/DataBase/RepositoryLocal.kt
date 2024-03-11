package com.example.marsphotos.DataBase

import com.example.marsphotos.model.KardexItem

class RepositoryLocal(private val databaseSicenet: DatabaseSicenet) {


    suspend fun obtenerKardex(): List<Kardex> {
        return  databaseSicenet.DaoSicenet().getAllKardex()
    }
}

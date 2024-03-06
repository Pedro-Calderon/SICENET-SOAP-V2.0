package com.example.marsphotos.DataBase
class RepositoryLocal(private val daoSicenet: DaoSicenet) {



    suspend fun insertDatosAlumno(datosAlumno: DatosAlumno) {
        daoSicenet.insertarAlumno(datosAlumno)
    }



    suspend fun insertAcceso(acceso: Acceso) {
        daoSicenet.insertAcceso(acceso)
    }
}

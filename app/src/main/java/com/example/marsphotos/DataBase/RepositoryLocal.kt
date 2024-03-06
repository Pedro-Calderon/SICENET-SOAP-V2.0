package com.example.marsphotos.DataBase
class RepositoryLocal(private val daoSicenet: DaoSicenet) {

    suspend fun getAllDatosAlumno(): List<DatosAlumno> {
        return daoSicenet.getDatosAlumno()
    }

    suspend fun insertDatosAlumno(datosAlumno: DatosAlumno) {
        daoSicenet.insertarAlumno(datosAlumno)
    }

    suspend fun getAllAcceso(): List<Acceso> {
        return daoSicenet.getAcceso()
    }

    suspend fun insertAcceso(acceso: Acceso) {
        daoSicenet.insertAcceso(acceso)
    }
}

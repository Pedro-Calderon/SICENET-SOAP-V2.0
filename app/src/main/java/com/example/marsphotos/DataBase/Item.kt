package com.example.marsphotos.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "Datosdelalumno")
data class DatosAlumno(

    @PrimaryKey()
    @ColumnInfo(name="id")
    val id:Int,
    @ColumnInfo(name = "Matricula")
    val itemMatricula: String,
    @ColumnInfo(name = "Nombre")
    val itemNombre: String,
    @ColumnInfo(name = "Carrera")
    val itemCarrera: String,
    @ColumnInfo(name = "Semestre")
    val itemSemestre: Int,
    @ColumnInfo(name = "FechaReins")
    val fechaReins: String,
    @ColumnInfo(name = "ModEducativo")
    val modEducativo: Int,
    @ColumnInfo(name = "Adeudo")
    val adeudo: Boolean,
    @ColumnInfo(name = "UrlFoto")
    val urlFoto: String,
    @ColumnInfo(name = "AdeudoDescripcion")
    val adeudoDescripcion: String,
    @ColumnInfo(name = "Inscrito")
    val inscrito: Boolean,
    @ColumnInfo(name = "Estatus")
    val estatus: String,
    @ColumnInfo(name = "CdtosAcumulados")
    val cdtosAcumulados: Int,
    @ColumnInfo(name = "CdtosActuales")
    val cdtosActuales: Int,
    @ColumnInfo(name = "Especialidad")
    val especialidad: String,
    @ColumnInfo(name = "Lineamiento")
    val lineamiento: Int
)

@Entity(tableName = "Acceso")
data class Acceso(
    @PrimaryKey()
    @ColumnInfo(name="id")
    val id:Int,
    @ColumnInfo(name = "Matricula")
    val itemMatricula: String,
    @ColumnInfo(name = "acceso")
    val itemacceso: String

)


@Entity(tableName = "Calificaciones")
data class CalificacionesEntity(
    @PrimaryKey()
    @ColumnInfo(name="id")
    val id: Int,
    val Observaciones: String?,
    val C13: String?,
    val C12: String?,
    val C11: String?,
    val C10: String?,
    val C9: String?,
    val C8: String?,
    val C7: String?,
    val C6: String?,
    val C5: String?,
    val C4: String?,
    val C3: String?,
    val C2: String?,
    val C1: String?,
    val UnidadesActivas:String?,
    val Materia: String?,
    val Grupo: String?
)

@Entity(tableName = "Calificacion_final")
data class  Califinal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    val id: Int=0,
    val calif: Int,
    val acred: String,
    val grupo: String,
    val materia: String,
    val Observacion: String
)
@Entity(tableName = "CargaAcademica")
data class CargaAcademica(
    @PrimaryKey()
    @ColumnInfo(name="id")
    val id: Int,
    val Semipresencial: String,
    val Observaciones: String,
    val Docente: String,
    val clvOficial: String,
    val Sabado: String,
    val Viernes: String,
    val Jueves: String,
    val Miercoles: String,
    val Martes: String,
    val Lunes: String,
    val EstadoMateria: String,
    val CreditosMateria: Int,
    val Materia: String,
    val Grupo: String,
)
@Entity(tableName = "Kardex")
data class Kardex(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Int=0,
    val S3:String?,
    val P3:String?,
    val A3:String?,
    val ClvMat:String?,
    val ClvOfiMat:String?,
    val Materia:String?,
    val Cdts:String?,
    val Calif:String?,
    val Acred:String?,
    val S1:String?,
    val P1:String?,
    val A1:String?,
    val S2:String?,
    val P2:String?,
    val A2:String?
)

@Entity(tableName = "Fecha")
data class UltimaConexion(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Int=0,
    val fehca:String
)
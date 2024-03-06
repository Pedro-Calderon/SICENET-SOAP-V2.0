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
    @ColumnInfo(name = "materia")
    val materia: String?,
    val observaciones: String?,
    val c1: String?,
    val c2: String?,
    val c3: String?,
    val c4: String?,
    val c5: String?,
    val c6: String?,
    val c7: String?,
    val c8: String?,
    val c9: String?,
    val c10: String?,
    val c11: String?,
    val c12: String?,
    val c13: String?,
    val grupo:String?
)
